package com.example.pfairplayservice.model.origin;

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
public class NeedTeamArticle {

    private int articleNo;

    private String writeMemberUid;

    private String subject;

    private String detail;

    private Position needPosition;

    private Date writeDate;

    private Date modifiedDate;

    private int viewCount;

    private Status status;

    public NeedTeamArticleEntity toNeedTeamArticleEntity(MemberEntity writeMember) {

        return NeedTeamArticleEntity.builder()
                .articleNo(articleNo)
                .writeMember(writeMember)
                .subject(subject)
                .detail(detail)
                .needPosition(needPosition.getPosition())
                .writeDate(writeDate)
                .modifiedDate(modifiedDate)
                .viewCount(viewCount)
                .status(Status.ONGOING.getStatus())
                .build();

    }


}
