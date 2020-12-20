package com.example.pfairplayservice.team;

import com.example.pfairplayservice.match.Match;
import com.example.pfairplayservice.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private List<Member> memberList;
    private List<Match> matchList;
    private Date REGISTRATION_DATE;
    private Date FOUND_DATE;
    private String teamName;
    private Grade grade;



}
