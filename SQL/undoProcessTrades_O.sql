CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `undoProcessTrades_O`(IN stamp1 varchar(255))
    SQL SECURITY INVOKER
BEGIN
    set SQL_SAFE_UPDATES=0;
    
    set @stamp = stamp1;
    
    drop temporary table if exists temp1;
    create temporary table temp1
    select * from positions
    where `timeStamp`=@stamp;
    
    update trades
    set processed='N' 
    where id in (select pos_id from temp1);
    
    delete from positions
    where `timeStamp`=@stamp;
    
    delete from `timeStamps`
    where `timeStamp`=@stamp;
    
END