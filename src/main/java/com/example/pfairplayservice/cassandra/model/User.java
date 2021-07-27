package com.example.pfairplayservice.cassandra.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@Table("user")
public class User {

    @PrimaryKey
    private Integer uid;

    private String name;

}
