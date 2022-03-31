drop schema if exists cs206;
create schema cs206;
use cs206; 
drop table if exists cart_products;
drop table if exists preset;
drop table if exists product;
drop table if exists user;
create table product(
	id bigint primary key,
    brand varchar(255),
    calories double,
    fat double, 
    fibre double,
    img_url varchar(255),
    name varchar(255),
    price double,
    protein double,
    sodium double ,
    sugar double,
    servingsize varchar(255)
);

create table user(
    id bigint primary key,
    authorities varchar(255),
    password varchar(255),
    username varchar(20)
);

create table preset(
    id bigint primary key,
    factor1 varchar(255),
    factor2 varchar(255),
    factor3 varchar(255),
    name varchar(255),
    user_id bigint,
    constraint preset_fk foreign key(user_id) references user(id)
);

create table cart_products(
    product_id bigint,
    user_id bigint,
    constraint cp_pk primary key (product_id,user_id),
    constraint cp_fk1 foreign key (product_id) references product(id),
    constraint cp_fk2 foreign key (user_id) references user(id)
);


delimiter $$ 
create procedure looksgood( IN nametype varchar(25) , IN factor1 varchar(25) , IN sort1 varchar(25),
IN factor2 varchar(25) , IN sort2 varchar(25),IN factor3 varchar(25) , IN sort3 varchar(25))
Begin
select  * , (0.5 * ranking1 + 0.3 * ranking2 + 0.2 * ranking3) as top from 
(select * ,ROW_NUMBER() OVER(
ORDER BY 
CASE WHEN sort1 = 'asc' THEN
			CASE
			WHEN factor1 = 'calories' THEN calories
			WHEN factor1 = 'price' THEN price  
            WHEN factor1 = 'sugar' THEN sugar
			WHEN factor1 = 'fibre' THEN fibre
			WHEN factor1 = 'fat' THEN fat
			WHEN factor1 = 'protein' THEN protein
			WHEN factor1 = 'sodium' THEN sodium
			END
		END ASC
		, CASE WHEN sort1 = 'desc' THEN
		CASE
			WHEN factor1 = 'calories' THEN calories
			WHEN factor1 = 'price' THEN price  
            WHEN factor1 = 'sugar' THEN sugar
			WHEN factor1 = 'fibre' THEN fibre
			WHEN factor1 = 'fat' THEN fat
			WHEN factor1 = 'protein' THEN protein
			WHEN factor1 = 'sodium' THEN sodium
		END	
		END DESC
) ranking1 , ROW_NUMBER() OVER(
ORDER BY 
CASE WHEN sort2 = 'asc' THEN
			CASE
			WHEN factor2 = 'calories' THEN calories
			WHEN factor2 = 'price' THEN price  
            WHEN factor2 = 'sugar' THEN sugar
			WHEN factor2 = 'fibre' THEN fibre
			WHEN factor2 = 'fat' THEN fat
			WHEN factor2 = 'protein' THEN protein
			WHEN factor2 = 'sodium' THEN sodium
			END
		END ASC
		, CASE WHEN sort2 = 'desc' THEN
		CASE
			WHEN factor2 = 'calories' THEN calories
			WHEN factor2 = 'price' THEN price  
            WHEN factor2 = 'sugar' THEN sugar
			WHEN factor2 = 'fibre' THEN fibre
			WHEN factor2 = 'fat' THEN fat
			WHEN factor2 = 'protein' THEN protein
			WHEN factor2 = 'sodium' THEN sodium
		END	
		END DESC ) ranking2,ROW_NUMBER() OVER(
ORDER BY 
CASE WHEN sort3 = 'asc' THEN
			CASE
			WHEN factor3 = 'calories' THEN calories
			WHEN factor3 = 'price' THEN price  
            WHEN factor3 = 'sugar' THEN sugar
			WHEN factor3 = 'fibre' THEN fibre
			WHEN factor3 = 'fat' THEN fat
			WHEN factor3 = 'protein' THEN protein
			WHEN factor3 = 'sodium' THEN sodium
			END
		END ASC
		, CASE WHEN sort3 = 'desc' THEN
		CASE
			WHEN factor3 = 'calories' THEN calories
			WHEN factor3 = 'price' THEN price  
            WHEN factor3 = 'sugar' THEN sugar
			WHEN factor3 = 'fibre' THEN fibre
			WHEN factor3 = 'fat' THEN fat
			WHEN factor3 = 'protein' THEN protein
			WHEN factor3 = 'sodium' THEN sodium
		END	
		END DESC ) ranking3 
        
        from product where name like Concat('%' , nametype ,'%') )as t1 order by top ;
end $$
delimiter ; 

delimiter $$ 
create procedure looksgood2( IN nametype varchar(25) , IN factor1 varchar(25) , IN sort1 varchar(25),
IN factor2 varchar(25) , IN sort2 varchar(25))
Begin
select  * , (0.7 * ranking1 + 0.3 * ranking2 ) as top from 
(select * , ROW_NUMBER() OVER(
ORDER BY 
CASE WHEN sort1 = 'asc' THEN
   CASE
   WHEN factor1 = 'calories' THEN calories
   WHEN factor1 = 'price' THEN price  
   WHEN factor1 = 'sugar' THEN sugar
   WHEN factor1 = 'fibre' THEN fibre
   WHEN factor1 = 'fat' THEN fat
   WHEN factor1 = 'protein' THEN protein
   WHEN factor1 = 'sodium' THEN sodium
   END
  END ASC
  , CASE WHEN sort1 = 'desc' THEN
  CASE
   WHEN factor1 = 'calories' THEN calories
   WHEN factor1 = 'price' THEN price  
   WHEN factor1 = 'sugar' THEN sugar
   WHEN factor1 = 'fibre' THEN fibre
   WHEN factor1 = 'fat' THEN fat
   WHEN factor1 = 'protein' THEN protein
   WHEN factor1 = 'sodium' THEN sodium
  END 
  END DESC
) ranking1 , ROW_NUMBER() OVER(
ORDER BY 
CASE WHEN sort2 = 'asc' THEN
   CASE
   WHEN factor2 = 'calories' THEN calories
   WHEN factor2 = 'price' THEN price  
   WHEN factor2 = 'sugar' THEN sugar
   WHEN factor2 = 'fibre' THEN fibre
   WHEN factor2 = 'fat' THEN fat
   WHEN factor2 = 'protein' THEN protein
   WHEN factor2 = 'sodium' THEN sodium
   END
  END ASC
  , CASE WHEN sort2 = 'desc' THEN
  CASE
   WHEN factor2 = 'calories' THEN calories
   WHEN factor2 = 'price' THEN price  
   WHEN factor2 = 'sugar' THEN sugar
   WHEN factor2 = 'fibre' THEN fibre
   WHEN factor2 = 'fat' THEN fat
   WHEN factor2 = 'protein' THEN protein
   WHEN factor2 = 'sodium' THEN sodium
  END 
  END DESC ) ranking2 
from product where name like Concat('%' , nametype ,'%') )as t1 order by top ;
end $$
delimiter ;
