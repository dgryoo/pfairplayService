package com.example.pfairplayservice.jpa.model;

import javax.persistence.Column;

public class NeedTeamArticle extends ArticleEntity {

    @Column
    private MemberEntity writeMember;

}
