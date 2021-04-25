package com.example.pfairplayservice.jpa.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleNo;

    @Column(nullable = false)
    @Length(min = 2, max = 20)
    private String subject;

    @Column(nullable = false)
    @Length(min = 1, max = 255)
    private String detail;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date writeDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    private int viewCount;

    @Column(nullable = false)
    private int status;

}