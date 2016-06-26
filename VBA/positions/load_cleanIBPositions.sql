USE pupone_EG_LOAD;


SET @max = (SELECT COUNT(*) FROM positions);
SET @max = - @max;
SET sql_mode = 'NO_UNSIGNED_SUBTRACTION';


LOAD DATA LOCAL INFILE
'/Users/luca/Dropbox/ELLE/ELLE Portfolio Management/4SQL/positions 4SQL/PositionsDL 111231 TEST-positions-60626R-4SQL.csv'
INTO TABLE positions
FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(
	inputLine,symbol,Q,@lot_Time,LS,price_adj,basis_adj,secType,filecode,account,multi,@O_Type,@expiry,@strike,underlying,yr
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

UPDATE positions SET expiry = NULL WHERE expiry = '0000-00-00';
