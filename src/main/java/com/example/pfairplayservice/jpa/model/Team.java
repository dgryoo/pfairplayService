package com.example.pfairplayservice.jpa.model;

import com.example.pfairplayservice.jpa.model.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")

public class Team {

    @Id
    @GeneratedValue(generator="team-uid", strategy = GenerationType.AUTO)
    @GenericGenerator(name="team-uid", strategy = "uuid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String tid;

    @Column(nullable = false)
    private String teamName;

    @OneToOne
    @Column(nullable = false)
    private Member teamLeadMember;

    @Column(nullable = false)
    private String activityAreaAddress;

    @OneToMany
    @Column
    private List<Member> memberList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CreationTimestamp
    @Column(nullable = false)
    private Date registrationDate;

    @Column
    private Date foundDate;

    // TODO 매치 생성 후 적용
    // private List<Match> matchList;

}
