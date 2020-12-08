package com.example.pfairplayservice.testjpa;

import lombok.Data;

import javax.persistence.*;

@Table(name = "user")
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String phoneNumber;

}
