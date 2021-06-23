package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.PlayGroundEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class MatchForPost {

    @NotNull
    @Min(value = 1, message = "PlayGroundNo must be greater or equal 1")
    private Integer playGroundNo;

    @NotNull
    @Min(value = 0, message = "Price must be greater or equal 0")
    private Integer price;

    @NotNull
    @NotBlank
    private String ownerTeamTid;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
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
