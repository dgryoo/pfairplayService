package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.PlayGroundEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MatchForPost {

    private int playGroundNo;

    private int price;

    private String ownerTeamTid;

    private Date startDate;

    private Date endDate;

    private String message;

    public MatchEntity toMatchEntity(TeamEntity ownerTeam, PlayGroundEntity playGroundEntity) {

        return MatchEntity.builder()
                .playGround(playGroundEntity)
                .price(price)
                .ownerTeam(ownerTeam)
                .startDate(startDate)
                .endDate(endDate)
                .message(message)
                .registrationDate(new Date())
                .viewCount(0)
                .status(0)
                .build();

    }
}
