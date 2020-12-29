CREATE TABLE MEMBER (
UID varchar(255) NOT NULL ,
name char(10) not null,
id varchar(10) not null,
password varchar(30) not null,
birthday Date not null,
address varchar(155) not null,
phone_number varchar(155) not null,
prefer_position int(11) ,
level int(11),
phone_number_disclosure_option int(11) default 1,
join_date Date NOT NULL DEFAULT (CURRENT_DATE),
recent_login_date Date NOT NULL DEFAULT (CURRENT_DATE),
PRIMARY KEY(UID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;