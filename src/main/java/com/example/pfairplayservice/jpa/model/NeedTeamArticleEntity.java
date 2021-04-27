package com.example.pfairplayservice.jpa.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "need_team_article")
public class NeedTeamArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int articleNo;

    @OneToOne
    private MemberEntity writeMember;

    @Column(nullable = false)
    @Length(min = 2, max = 20)
    private String subject;

    @Column(nullable = false)
    @Length(min = 1, max = 255)
    private String detail;

    @Column
    private int needPosition;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date writeDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int status;

}