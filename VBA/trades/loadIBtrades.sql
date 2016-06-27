/* loadIBtrades ++ scripts for loading trades ++ from IB.
Professor 2016-06-26

The following tables are loaded:
- trades
- allocations -> brokerIBmatches
- washes

The Excel download of the trades is first cleaned in VBA through cleanIBtrades VBA Macro.
This Macro cleans and formats the file and produces 4SQL.cvs files ready for upload.alter
*/

use pupone_EG_LOAD;

 set @`timeStamp`=Now();
 
 
/* trades for trades */
SET @max = (SELECT COUNT(*) FROM trades);

LOAD DATA LOCAL INFILE
'/Users/luca/Dropbox/ELLE/ELLE Portfolio Management/4SQL/trades 4SQL/U529048 Trades 2012 TEST-trades-60611P-4SQL.csv'
INTO TABLE trades 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	filecode,symbol,@trade_Time,underlying,@expiry,@strike,@O_Type,Xchange,Q,price,@dummy,proceeds,comm,basis,realized_PL,@dummy,codes,TotalQ,yr, `order`, inputLine,secType,bkrGroup,account,LS,OC,multi
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type),
    expiry = IF(@expiry = '', NULL, @expiry),
    trade_Time = STR_TO_DATE(@trade_Time, '%Y-%m-%d %H:%i:%s'),
    id = @max + inputLine,
    adj_proceeds = proceeds + comm,
    ksflag = '0';

insert into timeStamps (timeStamp,script,file) values(@`timeStamp`,'loadIBtrades', 'U529048 Trades 2012 TEST-trades-60611P-4SQL.csv');

/* brokerIBmatches for allocations */
SET @max = (SELECT COUNT(*) FROM brokerIBmatches);

LOAD DATA LOCAL INFILE
'/Users/luca/Dropbox/ELLE/ELLE Portfolio Management/4SQL/trades 4SQL/U529048 Trades 2012 TEST-allocations-60611P-4SQL.csv'
INTO TABLE brokerIBmatches 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	filecode,symbol,@lot_Time,underlying,@strike,@O_Type,Q,basis,realized_PL,codes,TotalQ,bkrType,yr, `order`, inputLine,secType,account,bkrGroup
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type),
    lot_Time = STR_TO_DATE(@lot_Time, '%Y-%m-%d %H:%i:%s'),
    id = @max + inputLine;

insert into timeStamps (timeStamp,script,file) values(@`timeStamp`,'loadIBtrades', 'U529048 Trades 2012 TEST-allocations-60611P-4SQL.csv');

/* brokerIBwashes for washes */
SET @max = (SELECT COUNT(*) FROM brokerIBwashes);

LOAD DATA LOCAL INFILE
'/Users/luca/Dropbox/ELLE/ELLE Portfolio Management/4SQL/trades 4SQL/U529048 Trades 2012 TEST-washes-60611P-4SQL.csv'
INTO TABLE brokerIBwashes 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	filecode,symbol,@lot_Time,underlying,@strike,@O_Type,Q,realized_PL,codes,bkrType,yr, `order`, inputLine,secType,account,bkrGroup
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type),
    lot_Time = STR_TO_DATE(@lot_Time, '%Y-%m-%d %H:%i:%s'),
    id = @max + inputLine;

insert into timeStamps (timeStamp,script,file) values(@`timeStamp`,'loadIBtrades', 'U529048 Trades 2012 TEST-washes-60611P-4SQL.csv');