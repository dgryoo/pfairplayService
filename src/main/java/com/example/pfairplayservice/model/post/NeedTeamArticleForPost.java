package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.NeedTeamArticleEntity;
import com.example.pfairplayservice.model.enumfield.Position;
import com.example.pfairplayservice.model.enumfield.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedTeamArticleForPost {

    private String writeMemberUid;

    private String subject;

    private String detail;

    private Position needPosition;

    public NeedTeamArticleEntity toNeedTeamArticleEntity(MemberEntity writeMember) {

        return NeedTeamArticleEntity.builder()
                .writeMember(writeMember)
                .subject(subject)
                .detail(detail)
                .needPosition(needPosition.getPosition())
                .writeDate(new Date())
                .modifiedDate(new Date())
                .viewCount(0)
                .status(Status.ONGOING.getStatus())
                .build();

    }

}
