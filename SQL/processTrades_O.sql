CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `processTrades_O`(IN MYTABLE varchar(50))
    SQL SECURITY INVOKER
BEGIN
	set @`timeStamp`=Now();
	set @rownum1=0;
	drop temporary table if exists tradesRecord_temporary;
    
    #filter the trades record with processed = N and OC=O
	SET @cmd3 = CONCAT('create temporary table if not exists tradesRecord_temporary
    select *,@rownum1 := @rownum1 + 1 AS rank from ',MYTABLE,'
    where processed = "N" and OC = "O" and ksflag<>"bk";');
	PREPARE STMT3 FROM @cmd3;
	EXECUTE STMT3;
	DEALLOCATE PREPARE STMT3;
    
    
	#duplicate same number positions table with pos_id =1
    set @rownum2=0;
	drop temporary table if exists positionsTable_temporary;
    create temporary table if not exists positionsTable_temporary
    select positionsTable.*,@rownum2 := @rownum2 + 1 AS rank from positions positionsTable
    right join 
    tradesRecord_temporary tradesALLTable
    on positionsTable.pos_id=1 and positionsTable.ksflag='0';
    
    #update the positions table value using the trades result by rank_id
    set SQL_SAFE_UPDATES=0;
	update positionsTable_temporary as t1,tradesRecord_temporary as t2
    set 
	t1.symbol=t2.symbol,
	t1.lot_Time=t2.trade_Time,
	t1.Q=t2.Q,
	t1.line=1,
	t1.OCE='O',
	t1.OCE_Time=t2.trade_Time,
	t1.LS=t2.LS,
	#t1.Qori=t2.Q,
	#t1.price_adj=t2.price,
	t1.basis_adj=t2.proceeds,
	t1.price=t2.price,
	t1.basis=t2.proceeds,
	t1.How='T',
	t1.wash='0',
	t1.ksflag=t2.ksflag,
	t1.codes=t2.codes,
	t1.account=t2.account,
	t1.yr=t2.yr,
	#t1.L_codes=t2.codes,
	t1.secType=t2.secType,
	t1.multi=t2.multi,
	t1.underlying=t2.underlying,
	t1.expiry=t2.expiry,
	t1.strike=t2.strike,
	t1.O_Type=t2.O_Type,
	t1.notes=t2.notes,
	t1.filecode=t2.filecode,
	t1.inputLine=t2.inputLine,
	t1.grp=t2.t_grp,
	t1.pos_id=t2.id,
    t1.`timeStamp`=@`timeStamp`
    where t1.rank=t2.rank;
    
    #drop rank to get positions table structure
	ALTER TABLE positionsTable_temporary DROP COLUMN rank;
    
    #insert into positions table
    set SQL_SAFE_UPDATES=0;
    insert into positions
    select t1.* from positionsTable_temporary t1;
    
    
    #update trades table from processed = N to Y
	SET @cmd4 = CONCAT('update ',MYTABLE,'
    set processed = "Y" 
    where processed = "N" and OC = "O" and ksflag <>"bk";');
	PREPARE STMT4 FROM @cmd4;
	EXECUTE STMT4;
	DEALLOCATE PREPARE STMT4;
    
    insert into `timeStamps` value(@`timeStamp`,'processTrades_O');
    
END