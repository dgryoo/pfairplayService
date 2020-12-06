package com.example.pfairplayservice.team;



public enum Grade {

    A("A","상", "상대불가"),
    B("B","중상", "존나잘함"),
    C("C", "중","선출다수"),
    D("D", "중하","소수선출"),
    E("E","하", "아마추어팀");

    private String grade;
    private String koreanGrade;
    private String description;

    Grade(String grade, String koreanGrade, String description) {
        this.grade = grade;
        this.koreanGrade = koreanGrade;
        this.description = description;
    }
}
