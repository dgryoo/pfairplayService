create table offer (
                       offer_no int primary key,
                       target_match_match_no int not null,
                       sand_team_tid varchar(255) not null,
                       receive_team_tid varchar(255) not null,
                       message varchar(255),
                       offer_date timestamp not null,
                       offer_status int not null,
                       foreign key (target_match_match_no) references soccer_match(match_no) on delete cascade,
                       foreign key (sand_team_tid) references team(tid) on delete cascade,
                       foreign key (receive_team_tid) references team(tid) on delete cascade
) engine=InnoDB default charset=utf8;