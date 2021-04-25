package com.example.pfairplayservice.jpa.model;

import javax.persistence.Column;

public class NeedMemberArticle extends ArticleEntity {

    @Column
    private TeamEntity writeTeam;

}