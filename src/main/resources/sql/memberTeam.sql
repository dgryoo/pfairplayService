create table member_team (
                             uid varchar(255),
                             tid varchar(255),
                             primary key (uid,tid),
                             foreign key (uid) references member(uid) on delete cascade,
                             foreign key (tid) references team(tid) on delete cascade
) engine=InnoDB default charset=utf8;