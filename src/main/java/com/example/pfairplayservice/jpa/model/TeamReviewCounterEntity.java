package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team_review_counter")
public class TeamReviewCounterEntity {

    @Id
    @Column(name = "review_id")
    private String reviewId;

    @Column(name = "thumbs_up_count",nullable = false)
    private int thumbsUpCount;

    @Column(name = "thumbs_down_count",nullable = false)
    private int thumbsDownCount;

}
