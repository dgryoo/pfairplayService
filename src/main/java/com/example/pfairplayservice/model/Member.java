package com.example.pfairplayservice.model;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    public Member(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String name;

    private List<Team> teamList = new ArrayList<>();

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

        Member result = Member.builder()
                .uid(memberEntity.getUid())
                .id(memberEntity.getId())
                .password(memberEntity.getPassword())
                .name(memberEntity.getName())
                .teamList(Team.fromList(memberEntity.getTeamEntityList()))
                .birthday(memberEntity.getBirthday())
                .address(memberEntity.getAddress())
                .phoneNumber(memberEntity.getPhoneNumber())
                .preferPosition(Position.from(memberEntity.getPreferPosition()))
                .level(memberEntity.getLevel())
                .phoneNumberDisclosureOption(memberEntity.getPhoneNumberDisclosureOption())
                .joinDate(memberEntity.getJoinDate())
                .recentLoginDate(memberEntity.getRecentLoginDate())
                .build();

        return result;
    }

    public static MemberEntity to(Member member) {

        MemberEntity result = MemberEntity.builder()
                .uid(member.getUid())
                .id(member.getId())
                .password(member.getPassword())
                .name(member.getName())
                .teamEntityList(Team.toList(member.getTeamList()))
                .birthday(member.getBirthday())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .preferPosition(member.getPreferPosition().getPosition())
                .level(member.getLevel())
                .phoneNumberDisclosureOption(member.getPhoneNumberDisclosureOption())
                .joinDate(member.getJoinDate())
                .recentLoginDate(member.getRecentLoginDate())
                .build();

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
