CREATE PROCEDURE `displayTradesToAggregate`(IN MYTABLE varchar(50))
BEGIN
	drop temporary table if exists aggregatedCalculation_temporary;
	SET @cmd1 = CONCAT('create temporary table if not exists aggregatedCalculation_temporary
    select l2.id as grp,sum(l2.Q) as sumOfQ,sum(l2.basis) as sumOfBasis,
    avg(l2.multi) as avgOfMulti, -sum(l2.proceeds)/sum(l2.Q)/avg(l2.multi) as price,sum(l2.comm) as sumOfComm,
    sum(l2.proceeds) as sumOfProceeds
    from ',MYTABLE,' l2
    where OC = "O" and !field(ksflag,"ks","bk","tot")
    and 
    exists(select "X" from ',MYTABLE,' group_team
    where group_team.symbol=l2.symbol and group_team.trade_Time=l2.trade_Time and
    group_team.price between l2.price-0.01 and l2.price+0.01 and group_team.id<>l2.id
    )
	group by l2.symbol,l2.Trade_Time
	having count(*)>1;');
    PREPARE STMT1 FROM @cmd1;
	EXECUTE STMT1;
	DEALLOCATE PREPARE STMT1;	
    
    drop temporary table if exists aggregatedTable_temporary;    
	SET @cmd2 = CONCAT('create temporary table if not exists aggregatedTable_temporary 
    select t1.* from ',MYTABLE,' t1
    right join 
    aggregatedCalculation_temporary t2
    on t1.id=t2.grp;');
	PREPARE STMT2 FROM @cmd2;
	EXECUTE STMT2;
	DEALLOCATE PREPARE STMT2;
    
    
    set SQL_SAFE_UPDATES=0;
    update aggregatedTable_temporary as t2 ,aggregatedCalculation_temporary as t1
    set t2.ksflag='tot',t2.Q=t1.sumOfQ,t2.basis=t1.sumOfBasis, t2.price=t1.price, t2.multi=t1.avgOfMulti,t2.t_grp=t1.grp,
    t2.comm=t1.sumOfComm, t2.proceeds=t1.sumOfProceeds
    where t2.id = t1.grp;
    
    
	drop temporary table if exists individualTable_temporary;
    
	SET @cmd3 = CONCAT('create temporary table if not exists individualTable_temporary 
    select t1.* from ',MYTABLE,' t1;');
	PREPARE STMT3 FROM @cmd3;
	EXECUTE STMT3;
	DEALLOCATE PREPARE STMT3;
    
	
	set SQL_SAFE_UPDATES=0;
	update individualTable_temporary as t1,aggregatedTable_temporary as t2
    set t1.ksflag='bk', t1.t_grp=t2.t_grp
    where t1.symbol=t2.symbol and t1.trade_Time=t2.trade_Time and
    t1.price between t2.price-0.01 and t2.price+0.01;
    
    select * from individualTable_temporary t1
    where t1.ksflag='bk'
    union 
    (select * from aggregatedTable_temporary)
    order by t_grp,FIELD(ksflag,'bk','tot');
    
END