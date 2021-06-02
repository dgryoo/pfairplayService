package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.model.enumfield.PlayGround;
import com.example.pfairplayservice.model.enumfield.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchForGet {

    private int matchNo;

    private PlayGround playGround;

    private int price;

    private TeamForGet ownerTeam;

    private TeamForGet guestTeam;

    private Date startDate;

    private Date endDate;

    private Date registrationDate;

    private Date modifiedDate;

    private int viewCount;

    private Status status;


    public static MatchForGet from(MatchEntity matchEntity) {
        if(matchEntity.getGuestTeam() == null) {
            return MatchForGet.builder()
                    .matchNo(matchEntity.getMatchNo())
                    .playGround(PlayGround.from(matchEntity.getGroundNumber()))
                    .price(matchEntity.getPrice())
                    .ownerTeam(TeamForGet.from(matchEntity.getOwnerTeam()))
                    .startDate(matchEntity.getStartDate())
                    .endDate(matchEntity.getEndDate())
                    .registrationDate(matchEntity.getRegistrationDate())
                    .modifiedDate(matchEntity.getModifiedDate())
                    .viewCount(matchEntity.getViewCount())
                    .status(Status.from(matchEntity.getStatus()))
                    .build();
        }
        return MatchForGet.builder()
                .matchNo(matchEntity.getMatchNo())
                .playGround(PlayGround.from(matchEntity.getGroundNumber()))
                .price(matchEntity.getPrice())
                .ownerTeam(TeamForGet.from(matchEntity.getOwnerTeam()))
                .guestTeam(TeamForGet.from(matchEntity.getGuestTeam()))
                .startDate(matchEntity.getStartDate())
                .endDate(matchEntity.getEndDate())
                .registrationDate(matchEntity.getRegistrationDate())
                .modifiedDate(matchEntity.getModifiedDate())
                .viewCount(matchEntity.getViewCount())
                .status(Status.from(matchEntity.getStatus()))
                .build();

    }
}
