create table soccer_match (
                              match_no int primary key,
                              play_ground_no int not null,
                              price int not null,
                              owner_team_tid varchar(255) not null,
                              guest_team_tid varchar(255),
                              start_date timestamp not null,
                              end_date timestamp not null,
                              message varchar(255),
                              registration_date timestamp not null,
                              modified_date timestamp,
                              view_count int not null,
                              status int not null,
                              foreign key (play_ground_no) references play_ground(play_ground_no) on delete cascade,
                              foreign key (owner_team_tid) references team(tid) on delete cascade,
                              foreign key (guest_team_tid) references team(tid)
) engine=InnoDB default charset=utf8;