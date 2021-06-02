create table soccer_match (
                              match_no int primary key,
                              ground_number int not null,
                              price int not null,
                              owner_team varchar(255) not null,
                              guest_team varchar(255),
                              start_date date not null,
                              end_date date not null,
                              message varchar(255),
                              registration_date timestamp not null,
                              modified_date timestamp,
                              view_count int not null,
                              status int not null,
                              foreign key (owner_team) references team(tid) on delete cascade,
                              foreign key (guest_team) references team(tid)
) engine=InnoDB default charset=utf8;