CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `aggregatePositions`()
    SQL SECURITY INVOKER
BEGIN
    # turn safe mode off
    set SQL_SAFE_UPDATES=0;
    
    set @`timeStamp`=Now();
    insert into `timeStamps` (timeStamp, script) values(@`timeStamp`,'aggregatePositions');
    
    # creates a temporary table sorted by pos_ID

    drop temporary table if exists orderedtable;
    create temporary table if not exists orderedtable
    select * from positions order by pos_id;

    # creates a temporary table from orderTable grouped by symbol and Trade_time with aggregate columns
    #only for the record which have close price, same symbol and same lot_time. The records which don't need to be aggregated will be filter out
   #Then it only aggregates the records which need to be aggregated
    
    drop temporary table if exists aggregatedCalculation_temporary;
    create temporary table if not exists aggregatedCalculation_temporary
    select max(l2.pos_id) as grp,sum(l2.Q) as sumOfQ,sum(l2.basis_adj) as sumOfBasis,
    avg(l2.multi) as avgOfMulti, sum(l2.basis_adj)/sum(l2.Q)/avg(l2.multi) as price_adj
    from orderedtable l2
    where OCE = "O" and !field(ksflag,"ks","bk")
    and 
    exists(select "X" from positions group_team
    where group_team.symbol=l2.symbol and group_team.lot_Time=l2.lot_Time and
    group_team.price_adj between l2.price_adj-0.01 and l2.price_adj+0.01 and group_team.pos_id<>l2.pos_id
    )
    group by l2.symbol,l2.lot_Time
    having count(*)>1;
    
    -- creates a temporary table with all of the fields from the current table and 
    -- showing records which exist in the current table and the aggregatedCalculation_temporary 
    -- table (grouped trades). Since we need to get data from the first bk record.

    drop temporary table if exists aggregatedTable_temporary;    
    create temporary table if not exists aggregatedTable_temporary 
    select t1.* from positions t1
    right join 
    aggregatedCalculation_temporary t2
    on t1.pos_id=t2.grp;
    
    -- replaces the fields in the above table
    -- with the aggregates created in the temporary table aggregatedCalculation_temporary
    update aggregatedTable_temporary as t2 ,aggregatedCalculation_temporary as t1
    set t2.ksflag='tot',t2.Q=t1.sumOfQ,t2.basis_adj=t1.sumOfBasis, t2.price_adj=t1.price_adj, t2.multi=t1.avgOfMulti,t2.grp=t1.grp,t2.line=t2.line+1,t2.`timeStamp`=@`timeStamp`
    where t2.pos_id = t1.grp;
    
    -- Back up a position table for change
    drop temporary table if exists individualTable_temporary;
    create temporary table if not exists individualTable_temporary 
    select t1.* from positions t1;
    
    -- replaces specified fields in the current table with the values 
    -- from the aggregated table where the price = the aggregate price
    set SQL_SAFE_UPDATES=0;
    update individualTable_temporary as t1,aggregatedTable_temporary as t2
    set t1.ksflag='bk', t1.grp=t2.grp,t1.`timeStamp`=@`timeStamp`
    where t1.symbol=t2.symbol and t1.lot_Time=t2.lot_Time and
    t1.price_adj between t2.price_adj-0.02 and t2.price_adj+0.02;
    
    /*
    set SQL_SAFE_UPDATES=0;
    update positions as t1,aggregatedTable_temporary as t2
    set t1.ksflag="bk", t1.grp=t2.grp
    where t1.symbol=t2.symbol and t1.lot_Time=t2.lot_Time and
    t1.price_adj between t2.price_adj-0.01 and t2.price_adj+0.01;
    */
    
    -- inserts the aggregated fields and records into the current table
    Insert into positions
    select * from aggregatedTable_temporary;
    
    -- inserts the aggregated records into the aggregated table 
    Insert into aggregatedPositions
    select * from aggregatedTable_temporary;
    
    Insert into aggregatedPositions
    select * from individualTable_temporary where ksflag="bk";
    
    -- delete original and bk records from the current table 
    delete from positions where pos_id in (select pos_id from aggregatedPositions) and ksflag<>"tot";
    
END