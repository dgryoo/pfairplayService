package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(generator = "team-tid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "team-tid", strategy = "uuid")
    private String tid;

    @Column(nullable = false)
    private String teamName;

    @OneToOne
    private MemberEntity teamLeadMember;

    @Column(nullable = false)
    private String activityAreaAddress;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date foundDate;

    // TODO 매치 생성 후 적용
    // private List<Match> matchList;

}
