create table team_review (
                             review_id varchar(255) primary key,
                             tid varchar(255) not null,
                             write_date timestamp not null,
                             reviewer_tid varchar(255) not null,
                             proper_team_level int not null,
                             team_manner_point int not null,
                             foreign key (tid) references team(tid) on delete cascade,
                             foreign key (reviewer_tid) references team(tid)
) engine=InnoDB default charset=utf8;