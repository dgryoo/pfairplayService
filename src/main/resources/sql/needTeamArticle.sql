create table need_team_article (
                                   article_no int primary key,
                                   write_member_uid varchar(255),
                                   subject varchar(20) not null,
                                   detail varchar(255) not null,
                                   write_date timestamp not null,
                                   modified_date timestamp not null,
                                   view_count int not null,
                                   status int not null,
                                   foreign key (write_member_uid) references member(uid) on delete cascade
) engine=InnoDB default charset=utf8;