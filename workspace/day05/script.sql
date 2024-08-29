use gb;
create table test_user(
	userid varchar(300) primary key,
    userpw varchar(300),
    username varchar(300)
);

create table test_board(
	boardnum int primary key auto_increment,
    boardtitle varchar(300),
    boardcontents varchar(3000),
    regdate datetime default now(),
    userid varchar(300)
);


select * from test_user;

select * from test_board;

delete from test_user where userid='durian';

delete from test_board;
ALTER TABLE test_board AUTO_INCREMENT = 3;