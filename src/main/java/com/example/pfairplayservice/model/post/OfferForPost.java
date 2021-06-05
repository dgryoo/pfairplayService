package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.OfferEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OfferForPost {

    private int targetMatchNo;

    private String sandTeamTid;

    private String receiveTeamTid;

    private String message;

    public OfferEntity toOfferEntity(MatchEntity targetMatch, TeamEntity sandTeam, TeamEntity receiveTeam) {

        return OfferEntity.builder()
                .targetMatch(targetMatch)
                .sandTeam(sandTeam)
                .receiveTeam(receiveTeam)
                .message(message)
                .offerDate(new Date())
                .offerStatus(0)
                .build();
    }

}
