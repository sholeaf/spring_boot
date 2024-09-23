create database project;
use project;
create table user(
	userid varchar(300) primary key,
	userpw varchar(300) not null,
	username varchar(300) not null,
	userphone varchar(300) not null,
	useremail varchar(300) not null,
	#유저 주소
	zipcode varchar(300) not null,
	addr varchar(1000),
	addrdetail varchar(2000) not null,
	addretc varchar(300),
  
	userpet bool,
	schedule varchar(3000)
);
insert user value("apple","asd1234","김사과","01012341234","apple@naver.com","서울시","강남구 서초동","~~~","301호","1","산책");
insert user value("banana","asd1234","반하나","01043214321","banana@naver.com","서울시","강남구 서초동","~~~","401호","0","산책");
select * from user;

#해쉬태그 추가? 추후
create table p_board(
	boardnum bigint primary key auto_increment,
	boardtitle varchar(300) not null,
	boardcontents varchar(300) not null,
	boardlikecnt bigint default 0,
	regdate datetime default now(), #등록시간
	updatedate datetime default now(), #수정시간
	userid varchar(300),
    
	boardflag bool default false #댓글 알림을 위한 컬럼
);
drop table p_board;
insert p_board (boardtitle,boardcontents,boardlikecnt,userid,boardflag) value("sample data","sample contents",3,"apple",0);
delete from p_board;
select * from p_board;

create table p_reply(
	replynum bigint primary key auto_increment,
	replycontent text not null,
	regdate datetime default now(),
	updatedate datetime default now(),
	replyuserid varchar(300),
	boardnum bigint
);

#p_pic -> p_file로 수정
create table p_file(
	systemname varchar(3000) not null,
  orgname varchar(3000) not null,
  boardnum bigint
);

create table likelist(
	userid varchar(300),
	pboardnum bigint
);

