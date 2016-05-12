CREATE  PROCEDURE `match`(in id int,in posid int)
BEGIN
	set @id= id;
    set @posid=posid;
		
	set SQL_SAFE_UPDATES=0;
        
    drop temporary table if exists temp6;
	create temporary table temp6
	select * from positions
	where pos_id = @posid and ksflag<>'bk';
    
    update temp6 as p_temp , trades as t_temp
	set p_temp.line = p_temp.line +1,
			p_temp.OCE_time = t_temp.trade_Time,
			p_temp.Q = p_temp.Q - t_temp.Q,
			p_temp.How = 'T',
            p_temp.T_id=t_temp.id,
			p_temp.`timeStamp`=@`timeStamp`
		where p_temp.pos_id = @posid and t_temp.id = @id;
		#after update Q, we can update basis_adj and OCE using if function
        update temp6 as p_temp , trades as t_temp
        set 
			p_temp.basis_adj = if(p_temp.Q = 0 , 0, p_temp.Q/(p_temp.Q+t_temp.Q) * basis_adj),
            p_temp.OCE= if (p_temp.Q=0, 'C' , p_temp.OCE)
		where p_temp.pos_id = @posid and t_temp.id = @id;
        
        #insert new record to position_temp table
        insert into positions
        select * from temp6;
        
        
		update trades
		set trades.processed = 'Y' ,locked='Y'
		where trades.id=@id;
    
END