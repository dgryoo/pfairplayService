package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

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

    @OneToMany
    @Column
    private List<MemberEntity> memberEntityList;

    @CreationTimestamp
    @Column(nullable = false)
    private Date registrationDate;

    @Column
    private Date foundDate;

    // TODO 매치 생성 후 적용
    // private List<Match> matchList;

}
