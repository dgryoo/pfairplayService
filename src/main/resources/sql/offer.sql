create table offer (
                       offer_no int primary key,
                       target_match int not null,
                       sand_team varchar(255) not null,
                       receive_team varchar(255) not null,
                       message varchar(255),
                       offer_date timestamp not null,
                       offer_status int not null,
                       foreign key (target_match) references soccer_match(match_no) on delete cascade,
                       foreign key (sand_team) references team(tid) on delete cascade,
                       foreign key (receive_team) references team(tid) on delete cascade
) engine=InnoDB default charset=utf8;