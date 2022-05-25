create table user(
	userid varchar(20) primary key,
    userpwd text,
    email varchar(50),
    username varchar(20),
    regtime timestamp default current_timestamp,
    isadmin boolean default false,
    visited int default 0
);

create table notice(
	articleno int auto_increment primary key,
    userid varchar(20),
    subject text,
    content text,
    regtime timestamp default current_timestamp
);

create table question(
	questionno int auto_increment primary key,
    userid varchar(20),
    subject text,
    content text,
    regtime timestamp default current_timestamp
);

create table answer(
	answerno int auto_increment primary key,
    questionno int,
    userid varchar(20),
    comment text,
    regtime timestamp default current_timestamp,
    foreign key answer(questionno) references question(questionno)
);

create table community(
	communityno int auto_increment primary key,
    userid varchar(20),
    subject text,
    content text,
    regtime timestamp default current_timestamp
);

create table reply(
	replyno int auto_increment primary key,
    communityno int,
    userid varchar(20),
    comment text,
    regtime timestamp default current_timestamp,
    foreign key reply(communityno) references community(communityno)
);

create table interest(
    userid varchar(20),
    sido varchar(30),
    gugun varchar(30),
    dong varchar(30),
    dongcode varchar(10) default 0,
    regtime timestamp default current_timestamp,
    primary key (userid, sido, gugun, dong)
);
