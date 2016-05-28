set SQL_SAFE_UPDATES=0;

drop temporary table if exists matches_temp;
create temporary table matches_temp
select *,year(lot_time) as year1,month(lot_time)as month1,day(lot_time) as date1 from matches;

drop temporary table if exists matches_temp2;
create temporary table matches_temp2
select *,if((month1=2 and date1=28),STR_TO_DATE(CONCAT(year1+1,'-',LPAD(03,2,'00'),'-',LPAD(01,2,'00')), '%Y-%m-%d'),STR_TO_DATE(CONCAT(year1+1,'-',LPAD(month1,2,'00'),'-',LPAD(date1+1,2,'00')), '%Y-%m-%d')) as LTclearDate from matches_temp;

update matches t1,matches_temp2 t2
set t1.term=if(date(t2.tradeTime)>t2.LTclearDate,'LT','ST')
where t1.t_rank=t2.t_rank;

select * from matches;