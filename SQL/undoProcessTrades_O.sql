DELIMITER $$

/* This procedure should only be run immediately following a prior processTrades_O - so that positions table ahs not eveolved further.
That procedure would have created poistions with that timestamp
*/


CREATE PROCEDURE `undoProcessTrades_O`(IN stamp1 varchar(255))
    SQL SECURITY INVOKER
    
BEGIN
    set SQL_SAFE_UPDATES=0;
    
    set @stamp = stamp1;
    
    drop temporary table if exists temp1;
    create temporary table temp1
    select distinct pos_id from positions
    where `timeStamp`=@stamp;
    
    update trades
    set processed='N' 
    where id in (select pos_id from temp1);
    
    delete from positions
    where pos_id in (select pos_id from temp1);
    
    delete from `timeStamps`
    where `timeStamp`=@stamp;
    
END$$

DELIMITER ;