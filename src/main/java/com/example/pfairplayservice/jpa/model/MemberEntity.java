package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @ManyToMany
    @JoinTable(name = "memberEntityTeamEntity"
            , joinColumns = {@JoinColumn(name = "memberEntityUid")}
            , inverseJoinColumns = {@JoinColumn(name = "teamEntityTid")})
    private List<TeamEntity> teamEntityList = new ArrayList<>();

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
    private Integer preferPosition;

    @Column
    private Integer level;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer phoneNumberDisclosureOption;

    @CreationTimestamp
    @Column(nullable = false)
    private Date joinDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date recentLoginDate;
    
}
