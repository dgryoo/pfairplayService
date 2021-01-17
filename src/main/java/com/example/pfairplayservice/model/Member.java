package com.example.pfairplayservice.model;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    public Member(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber= phoneNumber;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uid;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    private List<Team> teamList = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Date birthday;

    private String address;

    private String phoneNumber;

    private Position preferPosition;

    private Integer level;

    private Integer phoneNumberDisclosureOption;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date joinDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date recentLoginDate;

    public static Member from(MemberEntity memberEntity) {

        Member result = new Member(memberEntity.getUid(), memberEntity.getName(), memberEntity.getId(), Team.fromList(memberEntity.getTeamEntityList()), memberEntity.getPassword()
                , memberEntity.getBirthday(), memberEntity.getAddress(), memberEntity.getPhoneNumber(), Position.from(memberEntity.getPreferPosition())
                , memberEntity.getLevel(), memberEntity.getPhoneNumberDisclosureOption(), memberEntity.getJoinDate(), memberEntity.getRecentLoginDate());
        return result;
    }

    public static MemberEntity to(Member member) {
        MemberEntity result = new MemberEntity(member.getUid(),Team.toList(member.getTeamList()),member.getName(),member.getId()
            ,member.getPassword(),member.getBirthday(),member.getAddress(),member.getPhoneNumber(),member.getPreferPosition().getPosition()
            ,member.getLevel(),member.getPhoneNumberDisclosureOption(),member.getJoinDate(),member.getRecentLoginDate());
        return result;
    }

    public static List<MemberEntity> toList(List<Member> memberList) {
        if (memberList == null) return null;
        List<MemberEntity> memberEntityListTemp = null;
        memberList
                .stream()
                .forEach(member -> memberEntityListTemp.add(Member.to(member)));
        return memberEntityListTemp;
    }

    public static List<Member> fromList(List<MemberEntity> memberEntityList) {
        if (memberEntityList == null) return null;
        List<Member> memberListTemp = null;
        memberEntityList
                .stream()
                .forEach(memberEntity -> memberListTemp.add(Member.from(memberEntity)));
        return memberListTemp;
    }
}
