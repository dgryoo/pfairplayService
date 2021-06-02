package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.model.enumfield.PlayGround;
import com.example.pfairplayservice.model.enumfield.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchForGet {

    private int matchNo;

    private PlayGround playGround;

    private int price;

    private String ownerTeamTid;

    private String guestTeamTid;

    private Date startDate;

    private Date endDate;

    private Date registrationDate;

    private Date modifiedDate;

    private int viewCount;

    private Status status;


    public static MatchForGet from(MatchEntity matchEntity) {

        return MatchForGet.builder()
                .matchNo(matchEntity.getMatchNo())
                .playGround(PlayGround.from(matchEntity.getGroundNumber()))
                .price(matchEntity.getPrice())
                .ownerTeamTid(matchEntity.getOwnerTeam().getTid())
                .guestTeamTid(matchEntity.getGuestTeam().getTid())
                .startDate(matchEntity.getStartDate())
                .endDate(matchEntity.getEndDate())
                .registrationDate(matchEntity.getRegistrationDate())
                .modifiedDate(matchEntity.getModifiedDate())
                .viewCount(matchEntity.getViewCount())
                .status(Status.from(matchEntity.getStatus()))
                .build();

    }
}
