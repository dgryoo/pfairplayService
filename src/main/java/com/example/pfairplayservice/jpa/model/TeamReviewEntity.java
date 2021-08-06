package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team_review")
public class TeamReviewEntity {

    @Id
    @Column(name = "review_id")
    private String reviewId;

    @Column(name = "tid",nullable = false)
    private String tid;

    @Column(name = "write_date",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date writeDate;

    @Column(name = "reviewer_tid",nullable = false)
    private String reviewerTid;

    @Column(name = "review_detail",nullable = false)
    private String reviewDetail;

    @Column(name = "proper_team_level",nullable = false)
    private Integer properTeamLevel;

    @Column(name = "team_manner_point",nullable = false)
    private Integer teamMannerPoint;

}
