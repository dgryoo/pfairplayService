package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "offer")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int offerNo;

    @OneToOne
    @Column(nullable = false)
    private MatchEntity targetMatch;

    @OneToOne
    @Column(nullable = false)
    private TeamEntity sandTeam;

    @OneToOne
    @Column(nullable = false)
    private TeamEntity receiveTeam;

    @Column
    private String message;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date offerDate;

    @Column(nullable = false)
    private int offerStatus;

}
