package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity {

    // TODO : implement registerdTeamNames
    @Id
    @GeneratedValue(generator = "member-uid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "member-uid", strategy = "uuid")
    private String uid;

    @Column(nullable = false)
    @Length(min = 6, max = 10)
    private String id;

    @Column(nullable = false)
    @Length(min = 6, max = 10)
    private String password;

    @Column(nullable = false)
    @Length(min = 2, max = 10)
    private String name;

    @Column(nullable = false, length = 8)
    private Integer birthday;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(length = 1)
    private Integer preferPosition;

    @Column(length = 1)
    @ColumnDefault("1")
    private Integer level;


    @Column(nullable = false, length = 1)
    @ColumnDefault("1")
    private Integer phoneNumberDisclosureOption;

    @CreationTimestamp
    @Column(nullable = false)
    private Date joinDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date recentLoginDate;

}
