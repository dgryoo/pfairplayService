package com.example.pfairplayservice.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id
    private String UID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private Integer preferPositon;

    @Column
    private Integer level;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer phoneNumberDisclosureOption;

    @Column(nullable = false)
    private Date joinDate;

    @Column(nullable = false)
    private Date recentLoginDate;

}
