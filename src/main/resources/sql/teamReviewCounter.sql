create table team_review_counter (
                                     review_id varchar(255) primary key,
                                     thumbs_up_count int not null,
                                     thumbs_down_count int not null,
                                     foreign key (review_id) references team_review(review_id) on delete cascade
) engine=InnoDB default charset=utf8;