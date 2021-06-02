package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.model.enumfield.PlayGround;
import lombok.Data;

import java.util.Date;

@Data
public class MatchForPost {

    private PlayGround playGround;

    private int price;

    private String ownerTeamTid;

    private Date startDate;

    private Date endDate;

    private String message;

    public MatchEntity toMatchEntity(TeamEntity ownerTeam) {

        return MatchEntity.builder()
                .groundNumber(playGround.getGroundNumber())
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
