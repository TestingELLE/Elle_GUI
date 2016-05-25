CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `undoAggregatePositions`(IN MYTABLE varchar(50),IN stamp1 varchar(255))
    SQL SECURITY INVOKER
BEGIN
    set SQL_SAFE_UPDATES=0;
    
    set @stamp = stamp1;
    /*
    SET @cmd1 = CONCAT('update ',MYTABLE,'
    set ksflag = "0"
    where ksflag="bk" and FIND_IN_SET(grp,@numlist);');
    PREPARE STMT1 FROM @cmd1;
    EXECUTE STMT1;
    DEALLOCATE PREPARE STMT1;
    #FIND_IN_SET function can deal with list-like input. 
    #For example, find_in_set(1,'1,2,3')=1 will return the index the thing we are looking for.
    #find_in_set(2,'1,2,3')=2. find_in_set(4,'1,2.3')=0;    
    */
    SET @cmd2 = CONCAT('delete from aggregatedPositions
    where ksflag="tot"  and `timeStamp`=@stamp;');
    PREPARE STMT2 FROM @cmd2;
    EXECUTE STMT2;
    DEALLOCATE PREPARE STMT2;
    
    SET @cmd2 = CONCAT('update aggregatedPositions
    set ksflag = "0", grp = NULL
    where ksflag="bk"  and `timeStamp`=@stamp;');
    PREPARE STMT2 FROM @cmd2;
    EXECUTE STMT2;
    DEALLOCATE PREPARE STMT2;
    
    SET @cmd2 = CONCAT('insert into ',MYTABLE,'
    select * from aggregatedPositions where ksflag="0" and `timeStamp`=@stamp;');
    PREPARE STMT2 FROM @cmd2;
    EXECUTE STMT2;
    DEALLOCATE PREPARE STMT2;
    
    SET @cmd2 = CONCAT('delete from ',MYTABLE,'
    where ksflag="tot" and `timeStamp`=@stamp;');
    PREPARE STMT2 FROM @cmd2;
    EXECUTE STMT2;
    DEALLOCATE PREPARE STMT2;
    
    SET @cmd2 = CONCAT('update ',MYTABLE,'
    set `timeStamp`=NULL where `timeStamp`=@stamp;');
    PREPARE STMT2 FROM @cmd2;
    EXECUTE STMT2;
    DEALLOCATE PREPARE STMT2;

    SET @cmd2 = CONCAT('delete from aggregatedPositions
    where `timeStamp`=@stamp;');
    PREPARE STMT2 FROM @cmd2;
    EXECUTE STMT2;
    DEALLOCATE PREPARE STMT2;

    delete from `timeStamps`
    where `timeStamp`=@stamp;

END