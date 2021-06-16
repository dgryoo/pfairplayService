create table need_member_article (
                                     article_no int primary key,
                                     tid varchar(255),
                                     subject varchar(20) not null,
                                     detail varchar(255) not null,
                                     need_position int,
                                     write_date timestamp not null,
                                     modified_date timestamp not null,
                                     view_count int not null,
                                     status int not null,
                                     foreign key (tid) references team(tid) on delete cascade
) engine=InnoDB default charset=utf8;