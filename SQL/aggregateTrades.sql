CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `aggregateTrades`()
    SQL SECURITY INVOKER
BEGIN 
    -- turn safe mode off
    set SQL_SAFE_UPDATES=0;
      
    set @`timeStamp`=Now();
    
    insert into `timeStamps` (timeStamp, script) values (@`timeStamp`, 'aggregateTrades');
    
   -- creates a temporary table sorted by ID
    drop temporary table if exists orderedtable;
    create temporary table if not exists orderedtable
    select * from trades order by id;
   
    # creates a temporary table from orderTable grouped by symbol and Trade_time with aggregate columns
    #only for the record which have close price, same symbol and same lot_time. The records which don't need to be aggregated will be filter out
    #Then it only aggregates the records which need to be aggregated
    drop temporary table if exists aggregatedCalculation_temporary;
    create temporary table if not exists aggregatedCalculation_temporary
    select min(abs(l2.id)) as grp,sum(l2.Q) as sumOfQ,sum(l2.basis) as sumOfBasis,
    avg(l2.multi) as avgOfMulti, -sum(l2.proceeds)/sum(l2.Q)/avg(l2.multi) as price,sum(l2.comm) as sumOfComm,
    sum(l2.proceeds) as sumOfProceeds
    from orderedtable l2
    where OC = "O" and !field(ksflag,"ks","bk")
    and 
    exists(select "X" from trades group_team
    where group_team.symbol=l2.symbol and group_team.trade_Time=l2.trade_Time and
    group_team.price between l2.price-0.01 and l2.price+0.01 and group_team.id<>l2.id
    )
    group by l2.symbol,l2.Trade_Time
    having count(*)>1;
   
    -- creates a temporary table with all of the fields from the current table and 
    -- showing records which exist in the current table and the aggregatedCalculation_temporary 
    -- table (grouped trades) Since we need to get data from the first bk record.
    drop temporary table if exists aggregatedTable_temporary;    
    create temporary table if not exists aggregatedTable_temporary 
    select t1.* from trades t1
    right join 
    aggregatedCalculation_temporary t2
    on t1.id=t2.grp;
    
    -- replaces the fields in the above table
    -- with the aggregates created in the temporary table aggregatedCalculation_temporary
    update aggregatedTable_temporary as t2 ,aggregatedCalculation_temporary as t1
    set t2.ksflag='tot',t2.Q=t1.sumOfQ,t2.basis=t1.sumOfBasis, t2.price=t1.price, t2.multi=t1.avgOfMulti,t2.t_grp=t1.grp,
    t2.comm=t1.sumOfComm, t2.proceeds=t1.sumOfProceeds,t2.`timeStamp`=@`timeStamp`
    where t2.id = t1.grp;
    
    
    -- replaces specified fields in the current table with the values 
    -- from the aggregated table where the price = the aggregate price
    set SQL_SAFE_UPDATES=0;
    update trades as t1,aggregatedTable_temporary as t2
    set t1.ksflag="bk", t1.t_grp=t2.t_grp, t1.`timeStamp`=@`timeStamp`
    where t1.symbol=t2.symbol and t1.trade_Time=t2.trade_Time and
    t1.price between t2.price-0.01 and t2.price+0.01;
    
    
    -- inserts the aggregated fields and records into the current table
    Insert into trades
    select * from aggregatedTable_temporary
    order by underlying, symbol, t_grp,FIELD(ksflag,'bk','tot'),id;

    -- inserts the aggregated trades into the aggregatedTrades table 
    Insert into aggregatedTrades
    select * from aggregatedTable_temporary;
    
    Insert into aggregatedTrades
    select * from trades
    where ksflag="bk";
    
    #remove trades with ksflag = bk from the current table
    delete from trades where ksflag = "bk";
    

END