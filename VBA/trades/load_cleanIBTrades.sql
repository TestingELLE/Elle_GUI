USE pupone_EG;
DROP TABLE IF EXISTS wei_bbrokerIBmatches;
DROP TABLE IF EXISTS wei_bkrTrades;
CREATE TABLE wei_bbrokerIBmatches LIKE trades;
CREATE TABLE wei_bkrTrades LIKE trades;
SET @max = (SELECT COUNT(*) FROM wei_bkrTrades);

ALTER TABLE wei_bbrokerIBmatches DROP PRIMARY KEY;
ALTER TABLE wei_bbrokerIBmatches ADD PRIMARY KEY(inputLine);
ALTER TABLE wei_bbrokerIBmatches ADD bkrType ENUM('', 'allocation', 'wash', 'order');

LOAD DATA LOCAL INFILE
'/Users/ren/Desktop/intern/wei_new/trades/U529048 Trades 2012 TEST-Allocation and Wash 2012-60606S.csv'
INTO TABLE wei_bbrokerIBmatches 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	filecode,symbol,@trade_Time,underlying,@expiry,@strike,@O_Type,Q,price,basis,realized_PL,codes,TotalQ,bkrType,yr,inputLine,secType,account,bkrGroup
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type),
    expiry = IF(@expiry = '', NULL, @expiry), 
    trade_Time = STR_TO_DATE(@trade_Time, '%Y-%m-%d %H:%i:%s');


UPDATE wei_bbrokerIBmatches SET id = @max + inputLine;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN OC;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN LS; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN comm; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN proceeds; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN adj_proceeds;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN mflag;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN secType;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN multi;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN underlying; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN expiry;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN lot_Time; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN strike;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN O_Type;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN strategy; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN Xchange;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN fills;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN t_grp;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN matching; 
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN method;
ALTER TABLE wei_bbrokerIBmatches DROP COLUMN ksflag;

ALTER TABLE wei_bbrokerIBmatches MODIFY COLUMN symbol VARCHAR(24) NOT NULL AFTER id;
ALTER TABLE wei_bbrokerIBmatches MODIFY COLUMN bkrGroup int(11) AFTER trade_Time;
ALTER TABLE wei_bbrokerIBmatches CHANGE price bkr_price_adj decimal(12,6) NOT NULL;
ALTER TABLE wei_bbrokerIBmatches CHANGE basis bkr_basis_adj decimal(9,2) NOT NULL;
ALTER TABLE wei_bbrokerIBmatches CHANGE realized_PL bkr_realized_PL decimal(9,2);

USE pupone_EG;
DROP TABLE IF EXISTS wei_bkrTrades;
CREATE TABLE wei_bkrTrades LIKE trades;
SET @max = (SELECT COUNT(*) FROM wei_bkrTrades);

ALTER TABLE wei_bkrTrades DROP PRIMARY KEY;
ALTER TABLE wei_bkrTrades ADD PRIMARY KEY(inputLine);
ALTER TABLE wei_bkrTrades DROP OC;
ALTER TABLE wei_bkrTrades ADD OC enum('O', 'C', 'FX') NOT NULL AFTER trade_Time;

LOAD DATA LOCAL INFILE
'/Users/ren/Desktop/intern/wei_new/trades/U529048 Trades 2012 TEST-Trades-60606S-4SQL.csv'
INTO TABLE wei_bkrTrades 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	filecode,symbol,@trade_Time,underlying,@expiry,@strike,@O_Type,Xchange,Q,price,@dummy,proceeds,comm,basis,realized_PL,@dummy,codes,TotalQ,yr,inputLine,secType,bkrGroup,account,LS,OC,multi
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type),
    expiry = IF(@expiry = '', NULL, @expiry),
    trade_Time = STR_TO_DATE(@trade_Time, '%Y-%m-%d %H:%i:%s');

UPDATE wei_bkrTrades SET id = @max + inputLine;
UPDATE wei_bkrTrades SET adj_proceeds = proceeds + comm;
UPDATE wei_bkrTrades SET ksflag = '0';


ALTER TABLE wei_bkrTrades DROP PRIMARY KEY;
ALTER TABLE wei_bkrTrades ADD PRIMARY KEY(id, account, yr);



