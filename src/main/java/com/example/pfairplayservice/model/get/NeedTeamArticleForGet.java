package com.example.pfairplayservice.model.get;

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
public class NeedTeamArticleForGet {

    private int articleNo;

    private MemberForGet writeMember;

    private String subject;

    private String detail;

    private Position needPosition;

    private Date writeDate;

    private Date modifiedDate;

    private int viewCount;

    private Status status;

    public static NeedTeamArticleForGet from(NeedTeamArticleEntity needTeamArticleEntity) {

        return NeedTeamArticleForGet.builder()
                .articleNo(needTeamArticleEntity.getArticleNo())
                .writeMember(MemberForGet.from(needTeamArticleEntity.getWriteMember()))
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
