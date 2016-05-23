USE pupone_EG;
DROP TABLE IF EXISTS w_positions111231;
CREATE TABLE w_positions111231 LIKE positions;
SET @max = (SELECT COUNT(*) FROM w_positions111231);

ALTER TABLE w_positions111231 DROP PRIMARY KEY;
ALTER TABLE w_positions111231 ADD PRIMARY KEY(inputLine);

LOAD DATA LOCAL INFILE
'/Users/weiren/Desktop/trades_table_clean/loading/wei_positions_loading/PositionsDL 111231 TEST-positions-60520S-4SQL.csv'
INTO TABLE w_positions111231 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(
	inputLine,symbol,Q,@lot_Time,LS,price_adj,basis_adj,secType,filecode,account,multi,@o_type,@expiry,@strike,underlying
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    o_type = IF(@o_type = '', NULL, @o_type),
    lot_Time = STR_TO_DATE(@lot_Time, '%m/%d/%Y %H:%i'),
    expiry = STR_TO_DATE(@expiry, '%d-%b-%y');

UPDATE w_positions111231 SET expiry = NULL WHERE expiry = '0000-00-00';

UPDATE w_positions111231 SET inputLine = @max + inputLine;
UPDATE w_positions111231 SET OCE_Time = lot_Time;
UPDATE w_positions111231 SET pos_id = @max + inputLine;

ALTER TABLE w_positions111231 DROP PRIMARY KEY;
ALTER TABLE w_positions111231 ADD PRIMARY KEY(inputLine, account);



