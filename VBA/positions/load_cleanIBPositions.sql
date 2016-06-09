USE pupone_EG;
DROP TABLE IF EXISTS w_positions111231;
CREATE TABLE w_positions111231 LIKE positions;
SET @max = (SELECT COUNT(*) FROM w_positions111231);
SET @max = - @max;
SET sql_mode = 'NO_UNSIGNED_SUBTRACTION';

/*
ALTER TABLE w_positions111231 DROP PRIMARY KEY;
ALTER TABLE w_positions111231 ADD PRIMARY KEY(inputLine);
*/

LOAD DATA LOCAL INFILE
'/Users/ren/Desktop/intern/wei_new/positions/PositionsDL 111231 TEST_wei_06012016-positions-60605S-4SQL.csv'
INTO TABLE w_positions111231 
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	inputLine,symbol,Q,@lot_Time,LS,price_adj,basis_adj,secType,filecode,account,multi,@O_Type,@expiry,@strike,underlying
)
SET 
    strike = IF(@strike = '', NULL, @strike),
    O_Type = IF(@O_Type = '', NULL, @O_Type),
    lot_Time = STR_TO_DATE(@lot_Time, '%Y-%m-%d %H:%i:%s'),
    expiry = STR_TO_DATE(@expiry, '%Y-%m-%d %H:%i:%s'),
    inputLine = @max + inputLine,
    OCE_Time = lot_Time,
    pos_id = @max - inputLine,
    yr = Date_Format(lot_Time, "%Y");

UPDATE w_positions111231 SET expiry = NULL WHERE expiry = '0000-00-00';


/*
ALTER TABLE w_positions111231 DROP PRIMARY KEY;
ALTER TABLE w_positions111231 ADD PRIMARY KEY(pos_id, line, ksflag, account, yr);
*/