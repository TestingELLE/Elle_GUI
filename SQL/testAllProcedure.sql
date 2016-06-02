set net_read_timeout=360;
show variables like "net_read_timeout";

use pupone_dummy;
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

insert into trades select * from trades_ORI;
insert into positions select * from positions_ORI;

#trades(174),positions(202) 
#see the ksflag change procedure will make for trades and positions
call displayPositionsToAggregate('positions');
call displayTradesToAggregate('trades');

#aggregate positions table and trades table


call aggregatePositions('positions');
call aggregateTrades('trades');
#trades(164),positions(140) 
#see the positions and trades result
select * from positions;
select * from aggregatedPositions;

select * from trades;
select * from aggregatedTrades;
select * from `timeStamps`; 

call undoAggregatePositions('positions',(select `timeStamp` from `timeStamps` where `procedure`="aggregatePositions"));
call undoAggregateTrades('trades',(select `timeStamp` from `timeStamps` where `procedure`="aggregateTrades"));

call processTrades_O('trades');
#trades(164) positions(204)trades

call processTrades_C;


#trades(164) positions(271)
#have parameter to process before the given date-max_lot_time(the lot_time) in trades

select * from matches;
select * from noMatches;
select * from positions;
select * from trades;

#matches (67) nomatches(33) total(100)
call undoProcessTrades_('2016-05-23 09:37:53');

############## match 2 because 5.4 have two positions. and different larger than 1 minutes. 
#so we didn't aggregate it 
select * from positions
where underlying ='KOG';
select * from trades
where underlying = 'KOG';


call `match`(127,-166);
call undoMatch(127,-166);

select * from trades 
where id =127;
select * from positions
where pos_id=-166;


