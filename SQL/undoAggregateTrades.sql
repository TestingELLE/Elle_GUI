CREATE DEFINER=`pupone_Corinne`@`%` PROCEDURE `undoAggregateTrades`(IN stamp1 varchar(255))
    SQL SECURITY INVOKER
BEGIN
    set SQL_SAFE_UPDATES=0;

    set @stamp = stamp1;
    /*
    update trades
    set ksflag = "0" and `timeStamp`=null
    where ksflag="bk" and `timeStamp`=@stamp;
    
    */
    #FIND_IN_SET function can deal with list-like input. 
    #For example, find_in_set(1,'1,2,3')=1 will return the index the thing we are looking for.
    #find_in_set(2,'1,2,3')=2. find_in_set(4,'1,2.3')=0;    
    
    delete from trades
    where ksflag="tot" and `timeStamp`=@stamp;
   
    
    update aggregatedTrades
    set ksflag="0",`timeStamp`=NULL, t_grp=0
    where ksflag = "bk" and `timeStamp`=@stamp;
    
    
    insert into trades
    select * from aggregatedTrades
    where ksflag="0" and isnull(`timeStamp`);
    
    delete from aggregatedTrades
    where (ksflag="tot" and `timeStamp`=@stamp) or (ksflag="0" and isnull(`timeStamp`));

    
    delete from `timeStamps`
    where `timeStamp`=@stamp;

END