create table play_ground (
                             play_ground_no int primary key,
                             name varchar(255) not null,
                             main_address varchar(255) not null,
                             sub_address varchar(255) not null,
                             foreign key (main_address) references address(full_name)

) engine=InnoDB default charset=utf8;