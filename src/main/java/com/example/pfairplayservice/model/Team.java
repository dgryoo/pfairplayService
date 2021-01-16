package com.example.pfairplayservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Team {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String tid;

    private String teamName;

    private Member teamLeadMember;

    private String activityAreaAddress;

    private List<Member> memberList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date registrationDate;

    private Date foundDate;

}
