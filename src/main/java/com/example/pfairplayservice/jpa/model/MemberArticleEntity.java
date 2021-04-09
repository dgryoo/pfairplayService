package com.example.pfairplayservice.jpa.model;


import com.example.pfairplayservice.jpa.id.MemberArticleId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_article_list")
public class MemberArticleEntity {

    @EmbeddedId
    private MemberArticleId memberArticleId;

}
