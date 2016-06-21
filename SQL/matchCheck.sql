

use pupone_Shenrui;
set SQL_SAFE_UPDATES=0;
#drop old version positions and trades
delete from trades;
delete from positions;
delete from matches;
delete from noMatches;
delete from `timeStamps`;
delete from aggregatedTrades;
delete from aggregatedPositions;
#insert data from positions_ORI and trades_ORI


#trades(174),positions(202) 
#see the ksflag change procedure will make for trades and positions
#call displayPositionsToAggregate('positions');
#call displayTradesToAggregate('trades');

#aggregate positions table and trades table


delete from pupone_Shenrui.positions;
insert pupone_Shenrui.positions select * from pupone_EG.positions_ORI;

delete from pupone_Shenrui.trades;
insert pupone_Shenrui.trades select * from pupone_EG.trades;


call aggregatePositions('positions');
call aggregateTrades('trades');
#trades(176),positions(304) 
#see the positions and trades result
select * from positions;
select * from aggregatedPositions;

select * from trades;
select * from aggregatedTrades;
select * from `timeStamps`; 

#call undoAggregatePositions('positions',(select `timeStamp` from `timeStamps` where `procedure`="aggregatePositions"));
#call undoAggregateTrades('trades',"2016-05-10 14:29:30");

call processTrades_O('trades');
#trades(176) positions(368)

call processTrades_C;
#trades(176) positions(435)
#have parameter to process before the given date-max_lot_time(the lot_time) in trades
select * from matches;
select * from noMatches;
select * from positions;
select * from trades;

#matches (67) nomatches(33) total(100)
call undoProcessTrades_O('2016-04-30 19:28:43');


############## match 2 because 5.4 have two positions. and different larger than 1 minutes. 
#so we didn't aggregate it 
select * from positions
where underlying ='KOG';
select * from trades
where underlying = 'KOG';


select * from trades where symbol = 'ADSK' order by trade_Time;

select * from positions where symbol = 'ADSK' order by pos_id, line;

select * from noMatches  where p_symbol = 'ADSK' 
order by t_trade_Time;

select * from matches  where p_symbol = 'ADSK'; 

select * from `timeStamps`;

select * from trades
where OC='C' and ksflag<>'bk';

#######
select * from positionsTable_temporary;
select * from tradesRecord_temporary;

select *,@rownum1 := @rownum1 + 1 AS rank from trades
    where processed = "N" and OC = "O" and ksflag<>"bk";
    
describe trades;
    
call `match`(127,-155);
call undoMatch(127,-155);

select * from trades 
where id =127;
select * from positions
where pos_id=-155;


create table positions_3 like positions;
insert into positions_3 
(select * from positions
order by pos_id DESC);

select * from positions_1;
select * from positions_3;

select * from aggregatedPositions where ksflag="tot";

select * from temp7;

