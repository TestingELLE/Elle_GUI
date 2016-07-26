DELIMITER $$


CREATE PROCEDURE `undoAggregatePositions`(IN stamp1 varchar(255))
    SQL SECURITY INVOKER
    
BEGIN
    set SQL_SAFE_UPDATES=0;
    
    set @stamp = stamp1;
    /*
   update positions
    set ksflag = "0"
    where ksflag="bk" and FIND_IN_SET(grp,@numlist);
    
    #FIND_IN_SET function can deal with list-like input. 
    #For example, find_in_set(1,'1,2,3')=1 will return the index the thing we are looking for.
    #find_in_set(2,'1,2,3')=2. find_in_set(4,'1,2.3')=0;    
    */
    delete from aggregatedPositions
    where ksflag="tot"  and `timeStamp`=@stamp;
    
    
    update aggregatedPositions
    set ksflag = "0", grp = NULL
    where ksflag="bk"  and `timeStamp`=@stamp;

    
    insert into positions
    select * from aggregatedPositions where ksflag="0" and `timeStamp`=@stamp;
    
    delete from positions
    where ksflag="tot" and `timeStamp`=@stamp;
    
   update positions
    set `timeStamp`=NULL where `timeStamp`=@stamp;
    

    delete from aggregatedPositions
    where `timeStamp`=@stamp;
    

    delete from `timeStamps`
    where `timeStamp`=@stamp;

END$$

DELIMITER ;