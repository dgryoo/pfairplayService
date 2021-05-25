package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mid;

    @OneToOne
    @Column(nullable = false)
    private TeamEntity owner;

    @OneToOne
    private TeamEntity guest;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int groundNumber;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int status;

}
