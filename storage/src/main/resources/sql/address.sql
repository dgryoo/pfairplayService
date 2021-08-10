create table address(

                        address_code bigint primary key,
                        full_name varchar(255) not null unique,
                        state varchar(255) not null,
                        city varchar(255)

) engine=InnoDB default charset=utf8;

