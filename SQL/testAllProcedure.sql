use pupone_Shenrui;
set SQL_SAFE_UPDATES=0;
#drop old version positions and trades
truncate trades;
truncate positions;
truncate matches;
truncate noMatches;
truncate brokerIBmatches;
truncate brokerIBwashes;
truncate `timeStamps`;
truncate aggregatedTrades;
truncate aggregatedPositions;
show tables;

#at this point all tables are empty
select * from trades;
select * from positions;
#trades(0),positions(0) 

#import starting data
insert positions select * from pupone_EG_LOAD.positions;
insert trades select * from pupone_EG_LOAD.trades;
insert brokerIBmatches select * from pupone_EG_LOAD.brokerIBmatches;
insert brokerIBwashes select * from pupone_EG_LOAD.brokerIBwashes;
select * from trades;
select * from positions; 
select * from brokerIBmatches;
select * from brokerIBwashes;
#trades(832), positions(118), brokerIBmatches(910), brokerIBwashes(185)

#see the changes the aggregate procedures will make for trades and positions
select * from aggregatedPositions;
select * from aggregatedTrades;
# both 0 at this point
call displayPositionsToAggregate('positions');
call displayTradesToAggregate('trades');
# positionsToAggregate (88) ; tradesToAggregate (139)

#aggregate positions table
call aggregatePositions;
#positions(136)
#see the positions results
select * from aggregatedPositions order by underlying, symbol, pos_id;
select * from positions order by underlying, symbol, pos_id;
#aggregatedPositions (88) ; positions (70)

select * from `timeStamps`; 

#undo check
call undoAggregatePositions((select `timeStamp` from `timeStamps` where script="aggregatePositions"));

select `timeStamp` from `timeStamps` where script="aggregatePositions";


#aggregate trades table
call aggregateTrades;
#trades(98)

#see the trades results
select * from aggregatedTrades;
select * from trades order by underlying, symbol, id;
#aggregatedTrades (139) ; trades (775)




call undoAggregateTrades('trades',"2016-05-10 14:29:30");



call processTrades_O('trades');
select * from trades order by underlying, symbol, id;
select * from positions;
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

