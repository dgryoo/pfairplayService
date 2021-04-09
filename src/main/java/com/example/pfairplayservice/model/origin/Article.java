package com.example.pfairplayservice.model.origin;

import com.example.pfairplayservice.jpa.model.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private int articleNo;

    private String subject;

    private String detail;

    private Date writeDate;

    private Date modifiedDate;

    private int viewCount;

    private Status status;

    public static Article from(ArticleEntity articleEntity) {

        return Article.builder()
                .articleNo(articleEntity.getArticleNo())
                .subject(articleEntity.getSubject())
                .detail(articleEntity.getDetail())
                .writeDate(articleEntity.getWriteDate())
                .modifiedDate(articleEntity.getModifiedDate())
                .viewCount(articleEntity.getViewCount())
                .status(Status.from(articleEntity.getStatus()))
                .build();
    }

    public ArticleEntity toArticleEntity() {
        return ArticleEntity.builder()
                .articleNo(getArticleNo())
                .subject(getSubject())
                .detail(getDetail())
                .writeDate(getWriteDate())
                .modifiedDate(getModifiedDate())
                .viewCount(getViewCount())
                .status(getStatus().getStatus())
                .build();

    }

}
