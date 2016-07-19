/* loadIBtrades ++ scripts for loading trades ++ from IB.
Professor 2016-07-15

The following tables are loaded:
- trades
- brokerIBmatches
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
	inputLine,bkrGroup,symbol,@trade_Time,OC,LS,Q,price,proceeds,comm,adj_proceeds,basis,realized_PL,codes,account,yr,filecode,TotalQ,secType,multi,
    underlying,@expiry,@strike,@O_Type,Xchange,`order`,fills
)
SET 
    id = @max + inputLine,
    processed = 'N',
    trade_Time = STR_TO_DATE(@trade_Time, '%Y-%m-%d %H:%i:%s'),
    ksflag = '0',
    locked = 'N',
    expiry = IF(@expiry = '', NULL, @expiry),
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type)
    ;

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
	inputline,bkrGroup,symbol,Q,@lot_Time,basis,realized_PL,codes,`order`,TotalQ,yr,account,filecode
)
SET 
    id = @max + inputLine,
    processed = 'N',
    lot_Time = STR_TO_DATE(@lot_Time, '%Y-%m-%d %H:%i:%s');

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