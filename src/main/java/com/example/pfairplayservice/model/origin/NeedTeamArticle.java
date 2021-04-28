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

    private Member writeMember;

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

    public static NeedTeamArticle fromNeedTeamArticleEntity(NeedTeamArticleEntity needTeamArticleEntity) {

        return NeedTeamArticle.builder()
                .articleNo(needTeamArticleEntity.getArticleNo())
                .writeMember(Member.from(needTeamArticleEntity.getWriteMember()))
                .subject(needTeamArticleEntity.getSubject())
                .detail(needTeamArticleEntity.getDetail())
                .needPosition(Position.from(needTeamArticleEntity.getNeedPosition()))
                .writeDate(needTeamArticleEntity.getWriteDate())
                .modifiedDate(needTeamArticleEntity.getModifiedDate())
                .viewCount(needTeamArticleEntity.getViewCount())
                .status(Status.from(needTeamArticleEntity.getStatus()))
                .build();
    }


}
