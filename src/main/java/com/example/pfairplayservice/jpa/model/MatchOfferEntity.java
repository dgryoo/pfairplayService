package com.example.pfairplayservice.jpa.model;

import com.example.pfairplayservice.jpa.id.MatchOfferId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_offer")
public class MatchOfferEntity {

    @EmbeddedId
    private MatchOfferId matchOfferId;

}
