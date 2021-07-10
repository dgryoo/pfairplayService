package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamForPost {

    private String teamName;

    private String teamLeadMemberUid;

    private int level;

    private String activityAreaAddress;

    private Date foundDate;

    public TeamEntity toTeamEntity(MemberEntity memberEntity) {

        return TeamEntity.builder()
                .teamName(getTeamName())
                .level(1)
                .teamLeadMember(memberEntity)
                .activityAreaAddress(getActivityAreaAddress())
                .registrationDate(new Date())
                .foundDate(getFoundDate())
                .recommendCount(0)
                .matchCount(0)
                .rating(level * 200)
                .build();
    }

}
