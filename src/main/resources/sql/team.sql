create table team (
                      tid varchar(255) primary key,
                      team_name varchar(10) not null ,
                      team_lead_member_uid varchar(255) not null,
                      level int not null,
                      activity_area_address varchar(255) not null,
                      registration_date date not null,
                      found_date date not null,
                      recommend_count int not null,
                      match_count int not null,
                      rating int not null,
                      foreign key (team_lead_member_uid) references member(uid)
) engine=InnoDB default charset=utf8;