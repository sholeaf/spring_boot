use gb;
create table test_user(
	userid varchar(300) primary key,
    userpw varchar(300),
    username varchar(300)
);
create table test_board(
	boardnum int primary key default 1 auto_increment,
    boardtitle varchar(300),
    boardcontents varchar(3000),
    regdate datetime default now(),
    userid varchar(300)
);

select * from test_user;
delete from test_user;