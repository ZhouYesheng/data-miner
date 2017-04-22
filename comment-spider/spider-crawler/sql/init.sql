create table scs_comment_log(
id int PRIMARY KEY auto_increment,
md5 varchar(100),
productId varchar(20),
tags text,
create_time varchar(20),
score int,
content text,
insert_time varchar(20),
url varchar(300) ,
`type` varchar(20),
create_timestamp bigint
) CHARACTER SET utf8;

create table scs_word_index(id int PRIMARY KEY auto_increment,word varchar(30))  CHARACTER SET utf8;

create table scs_comment_seed(
id int PRIMARY key auto_increment,
md5 varchar(100),
url varchar(300),
body text,
status int,
`type` varchar(20)
) CHARACTER SET utf8;


create table scs_comment_keyword(id int PRIMARY key auto_increment,md5 varchar(100),wordtext varchar(30),weight double) CHARACTER SET utf8;


