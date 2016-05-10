CREATE PROCEDURE `undoProcessTrades_C`(IN timeStampinput2 varchar(255))
    SQL SECURITY INVOKER
BEGIN
    set SQL_SAFE_UPDATES=0;
    
    set @timeStampinput = timeStampinput2;
    
    update trades
    set processed='N',`timeStamp`=NULL,mflag=0
    where `timeStamp` =@timeStampinput;
    
    delete from positions
    where `timeStamp`= @timeStampinput;
 
    delete from matches
    where `timeStamp`= @timeStampinput;
    
    delete from noMatches
    where `timeStamp`= @timeStampinput;
    
    delete from `timeStamps`
    where `timeStamp`=@timeStampinput;
END