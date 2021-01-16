package com.example.pfairplayservice.jpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    // TODO : implement registerdTeamNames
    @Id
    @GeneratedValue(generator="member-uid", strategy = GenerationType.AUTO)
    @GenericGenerator(name="member-uid", strategy = "uuid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uid;

    @Column(nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CreationTimestamp
    @Column(nullable = false)
    private Date joinDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @UpdateTimestamp
    @Column(nullable = false)
    private Date recentLoginDate;

}
