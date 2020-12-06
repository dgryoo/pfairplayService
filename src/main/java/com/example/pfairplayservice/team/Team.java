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
    private Date FOUND_DATE;
    private List<Match> matchList;
    private String name;
    private Grade grade;



}
