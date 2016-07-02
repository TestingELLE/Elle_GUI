CREATE PROCEDURE `displayPositionsToAggregate`()
BEGIN	
	drop temporary table if exists aggregatedCalculation_temporary;
	
create temporary table if not exists aggregatedCalculation_temporary;
    select l2.pos_id as grp,sum(l2.Q) as sumOfQ,sum(l2.basis_adj) as sumOfBasis,
    avg(l2.multi) as avgOfMulti, sum(l2.basis_adj)/sum(l2.Q)/avg(l2.multi) as price_adj
    from ',MYTABLE,' l2
    where OCE = "O" and !field(ksflag,"ks","bk","tot")
    and 
    exists(select "X" from ',MYTABLE,' group_team
    where group_team.symbol=l2.symbol and group_team.lot_Time=l2.lot_Time and
    group_team.price_adj between l2.price_adj-0.01 and l2.price_adj+0.01 and group_team.pos_id<>l2.pos_id
    )
	group by l2.symbol,l2.lot_Time
	having count(*)>1;');
    PREPARE STMT1 FROM @cmd1;
	EXECUTE STMT1;
	DEALLOCATE PREPARE STMT1;	
    
    drop temporary table if exists aggregatedTable_temporary;    
	SET @cmd2 = CONCAT('create temporary table if not exists aggregatedTable_temporary 
    select t1.* from ',MYTABLE,' t1
    right join 
    aggregatedCalculation_temporary t2
    on t1.pos_id=t2.grp;');
	PREPARE STMT2 FROM @cmd2;
	EXECUTE STMT2;
	DEALLOCATE PREPARE STMT2;
    
    
    set SQL_SAFE_UPDATES=0;
    update aggregatedTable_temporary as t2 ,aggregatedCalculation_temporary as t1
    set t2.ksflag='tot',t2.Q=t1.sumOfQ,t2.basis_adj=t1.sumOfBasis, t2.price_adj=t1.price_adj, t2.multi=t1.avgOfMulti,t2.grp=t1.grp
    where t2.pos_id = t1.grp;
    
    
	drop temporary table if exists individualTable_temporary;
    
	SET @cmd3 = CONCAT('create temporary table if not exists individualTable_temporary 
    select t1.* from ',MYTABLE,' t1;');
	PREPARE STMT3 FROM @cmd3;
	EXECUTE STMT3;
	DEALLOCATE PREPARE STMT3;
    
	
	set SQL_SAFE_UPDATES=0;
	update individualTable_temporary as t1,aggregatedTable_temporary as t2
    set t1.ksflag='bk', t1.grp=t2.grp
    where t1.symbol=t2.symbol and t1.lot_Time=t2.lot_Time and
    t1.price_adj between t2.price_adj-0.01 and t2.price_adj+0.01;
    
    select * from individualTable_temporary t1
    where t1.ksflag='bk'
    union 
    (select * from aggregatedTable_temporary)
    order by grp,FIELD(ksflag,'bk','tot');
END