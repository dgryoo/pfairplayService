package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.MatchEntity;
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

    private Integer matchNo;

    private PlayGroundForGet playGround;

    private Integer price;

    private TeamForGet ownerTeam;

    private TeamForGet guestTeam;

    private Date startDate;

    private Date endDate;

    private Date registrationDate;

    private Date modifiedDate;

    private Integer viewCount;

    private Status status;

    private Integer ownerScore;

    private Integer guestScore;


    public static MatchForGet from(MatchEntity matchEntity) {
        if (matchEntity.getGuestTeam() != null) {
            return MatchForGet.builder()
                    .matchNo(matchEntity.getMatchNo())
                    .playGround(PlayGroundForGet.from(matchEntity.getPlayGround()).summarizeThis())
                    .price(matchEntity.getPrice())
                    .ownerTeam(TeamForGet.from(matchEntity.getOwnerTeam()).summarizeThis())
                    .guestTeam(TeamForGet.from(matchEntity.getGuestTeam()).summarizeThis())
                    .startDate(matchEntity.getStartDate())
                    .endDate(matchEntity.getEndDate())
                    .registrationDate(matchEntity.getRegistrationDate())
                    .modifiedDate(matchEntity.getModifiedDate())
                    .viewCount(matchEntity.getViewCount())
                    .status(Status.from(matchEntity.getStatus()))
                    .ownerScore(matchEntity.getOwnerScore())
                    .guestScore(matchEntity.getGuestScore())
                    .build();
        } else {
            return MatchForGet.builder()
                    .matchNo(matchEntity.getMatchNo())
                    .playGround(PlayGroundForGet.from(matchEntity.getPlayGround()).summarizeThis())
                    .price(matchEntity.getPrice())
                    .ownerTeam(TeamForGet.from(matchEntity.getOwnerTeam()).summarizeThis())
                    .guestTeam(null)
                    .startDate(matchEntity.getStartDate())
                    .endDate(matchEntity.getEndDate())
                    .registrationDate(matchEntity.getRegistrationDate())
                    .modifiedDate(matchEntity.getModifiedDate())
                    .viewCount(matchEntity.getViewCount())
                    .status(Status.from(matchEntity.getStatus()))
                    .ownerScore(matchEntity.getOwnerScore())
                    .guestScore(matchEntity.getGuestScore())
                    .build();
        }


    }

    public MatchForGet summarizeThis() {

        return MatchForGet.builder()
                .matchNo(matchNo)
                .playGround(playGround)
                .price(null)
                .ownerTeam(ownerTeam)
                .guestTeam(null)
                .startDate(startDate)
                .endDate(endDate)
                .registrationDate(registrationDate)
                .modifiedDate(null)
                .viewCount(viewCount)
                .status(status)
                .ownerScore(null)
                .guestScore(null)
                .build();
    }
}
