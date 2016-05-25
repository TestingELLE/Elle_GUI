CREATE DEFINER=`pupone_Shenrui`@`%` PROCEDURE `processTrades_C`()
    SQL SECURITY INVOKER
BEGIN

	set SQL_SAFE_UPDATES=0;
    
    set @`timeStamp`=Now();
    
    
    
    drop temporary table if exists position_temp;
	create temporary table position_temp
    SELECT s1.*
	FROM positions s1
	LEFT JOIN positions s2 ON s1.pos_id = s2.pos_id AND s1.line < s2.line
	WHERE s2.symbol IS NULL;
    
    delete from position_temp 
    where pos_id in (select pos_id from aggregatedPositions)
    and ksflag='0';
    
    
	#Get the record with OC='C' in trades table
	drop temporary table if exists temp3;
	create temporary table temp3
	select l1.* from trades l1
	where OC='C' and ksflag<>'bk'
	order by underlying, trade_time;


	#Using left join to find match record for temp3 from position_temp table. 
	#match record have same symbol, lot_Time, 
	drop temporary table if exists temp4;
	create temporary table temp4
	select temp3.id as t_id, temp3.underlying as t_underlying, temp3.symbol as t_symbol, temp3.Q as t_Q, temp3.lot_Time as t_lot_Time,
	temp3.trade_Time as t_trade_Time, temp3.basis as t_basis, temp3.realized_PL as t_realized_PL, 
	temp3.proceeds as t_proceeds, 
	l1.symbol as p_symbol, l1.Q as p_Q,l1.lot_time as p_lot_Time,
	l1.pos_id as p_id, l1.line as p_line,
	l1.OCE as p_OCE, l1.OCE_time as p_OCE_time, l1.price_adj as p_price_adj, 
	l1.basis_adj as p_basis_adj, l1.price as p_price, l1.basis as p_basis, l1.ksflag as p_ksflag,l1.yr as p_yr, l1.account as p_account
	from temp3
	left join position_temp l1
	on temp3.symbol = l1.symbol and if(TIME (temp3.lot_time)='00:00:00',date (temp3.lot_Time) =date (l1.lot_Time),temp3.lot_Time=l1.lot_Time)
	where l1.ksflag<>'bk' or isnull(l1.ksflag);


	#count how many matches we have for the record where there are matches.
	drop temporary table if exists temp7;
	create temporary table temp7
	select count(*) as `matches`,temp4.*,@`timeStamp` as `timeStamp` from temp4
	where  !isnull(temp4.p_Q)
	group by t_id;
   
   
    #union the matched record with no match record and multiple match
    drop temporary table if exists nomatch_temp;
	create temporary table nomatch_temp
	select 0,temp4.*,@`timeStamp` as `timeStamp` from temp4
	where isnull(temp4.p_Q)
    union 
    (select * from temp7 where matches>1);
    
  
    #get multiple match record
    drop temporary table if exists delete_list;
	create temporary table delete_list
    select distinct l2.* from nomatch_temp l1
    left join temp7 l2
    on l1.t_symbol = l2.t_symbol
    where l1.t_trade_time<=l2.t_trade_time and !isnull(l1.p_Q);


#################

    #get no match record
    drop temporary table if exists delete_list1;
	create temporary table delete_list1
    select * from nomatch_temp
    where isnull(p_Q);
   


    #insert multiple match, no match and record after these nomatch or multiple match to noMatches table.
    truncate table noMatches;
	insert into noMatches select * from delete_list;
	insert into noMatches select * from delete_list1;
   
 
    
    #delete these records from all nonull match trades table(which is already after group) 
    delete from temp7
    where t_id in (select t_id from delete_list);
    delete from temp7
    where t_id in (select t_id from delete_list1);
    
 
 
    #order trades table by underlying, pos_id , trades_time
    set @rownum1=0;
    drop temporary table if exists temp5;
	create temporary table temp5
	select *, @rownum1:=@rownum1+1 as t_rank from temp7
    order by t_underlying,p_id, t_trade_time;

    
	#insert to matches table
	insert into matches select t_id,t_symbol,t_Q,t_trade_Time,p_id,p_line,p_lot_time,t_proceeds,t_basis,t_realized_PL,NULL,NULL,p_symbol,p_Q,t_lot_Time,p_OCE,p_OCE_time,p_price_adj,p_basis_adj,p_price,p_basis,`timeStamp`,t_rank,p_yr,p_account from temp5;

   
    update trades, temp5
	set trades.processed = 'Y',trades.`timeStamp`=@`timeStamp`
    where trades.id  = temp5.t_id;
    #update trades table's processed
	
    update trades, noMatches
	set trades.mflag=noMatches.matches
    where trades.id  = noMatches.t_id;
	
	#drop temporary table temp7;
    
    
    #How many times we have in that loop.
    set @num_row= (select count(*) from temp5);
	
    #fetch first record in the position_temp
    set @x =1;
    drop temporary table if exists temp6;
	create temporary table temp6
	select * from position_temp 
	where pos_id = (select p_id from temp5 where t_rank = @x);
    
    #loop through every row in trades
    
    WHILE @x <= @num_row DO

        #if the next record have same matched pos_id then we use the position_temp row we have updated
        #Because it have right Q, line, etc
		if (select pos_id from temp6) <> (select p_id from temp5 where t_rank=@x) then
			drop temporary table if exists temp6;
			create temporary table temp6
			select * from position_temp 
			where pos_id = (select p_id from temp5 where t_rank = @x) and ksflag<>'bk';
        END IF;
        
        #Update position_temp row using @x row in trades
        update temp6 as p_temp , temp5 as t_temp
        set p_temp.line = p_temp.line +1,
			p_temp.OCE_time = t_temp.t_trade_Time,
			p_temp.Q = p_temp.Q - t_temp.t_Q,
			p_temp.How = 'T',
            p_temp.T_id=t_temp.t_id,
			p_temp.`timeStamp`=@`timeStamp`
		where p_temp.pos_id = t_temp.p_id and  t_temp.t_rank=@x;

		#after update Q, we can update basis_adj and OCE using if function
        update temp6 as p_temp , temp5 as t_temp
        set 
			p_temp.basis_adj = if(p_temp.Q = 0 , 0, p_temp.Q/(p_temp.Q+t_temp.t_Q) * basis_adj),
            p_temp.OCE= if (p_temp.Q=0, 'C' , p_temp.OCE)
		where p_temp.pos_id = t_temp.p_id and  t_temp.t_rank=@x;
        
        #insert new record to position_temp table
        insert into positions
        select * from temp6;
        
        #@x increment by 1 to finish the loop
        set @x =1+@x;
        
	END WHILE;
	
	insert into `timeStamps` value(@`timeStamp`,"processTrades_C");
/*  */
    
END