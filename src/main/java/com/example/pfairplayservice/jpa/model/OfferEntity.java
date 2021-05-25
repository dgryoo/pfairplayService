package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "offer")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int oid;

    @OneToOne
    @Column(nullable = false)
    private MatchEntity matchEntity;

    @OneToOne
    @Column(nullable = false)
    private TeamEntity sander;

    @OneToOne
    @Column(nullable = false)
    private TeamEntity receiver;

    private int offerStatus;

}
