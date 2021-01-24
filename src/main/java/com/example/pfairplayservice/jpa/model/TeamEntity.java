package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(generator = "team-uid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "team-uid", strategy = "uuid")
    private String tid;

    @Column(nullable = false)
    private String teamName;

    @OneToOne
    private MemberEntity teamLeadMember;

    @Column(nullable = false)
    private String activityAreaAddress;

    @CreationTimestamp
    @Column(nullable = false)
    private Date registrationDate;

    @Column
    private Date foundDate;

    // TODO 매치 생성 후 적용
    // private List<Match> matchList;

}
