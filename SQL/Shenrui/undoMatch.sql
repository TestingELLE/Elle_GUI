CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `undoMatch`(in id int,in posid int)
BEGIN
	set @id= id;
    set @posid=posid;
		
	set SQL_SAFE_UPDATES=0;
        
    delete from positions
    where pos_id=@posid and T_id = @id;
        
        
	update trades
	set trades.processed = 'N' ,locked='N'
	where trades.id=@id;
    
END