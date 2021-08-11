package model.summarized;

import mysql.model.NeedTeamArticleEntity;
import model.enumfield.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummarizedNeedTeamArticle {

    private int articleNo;

    private String subject;

    private String writeMemberName;

    private Date writeDate;

    private int viewCount;

    private Status status;

    public static SummarizedNeedTeamArticle fromNeedTeamArticleEntity(NeedTeamArticleEntity needTeamArticleEntity) {

        return SummarizedNeedTeamArticle.builder()
                .articleNo(needTeamArticleEntity.getArticleNo())
                .subject(needTeamArticleEntity.getSubject())
                .writeMemberName(needTeamArticleEntity.getWriteMember().getName())
                .writeDate(needTeamArticleEntity.getWriteDate())
                .viewCount(needTeamArticleEntity.getViewCount())
                .status(Status.from(needTeamArticleEntity.getStatus()))
                .build();
    }


}
