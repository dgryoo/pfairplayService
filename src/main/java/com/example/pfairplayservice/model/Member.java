package com.example.pfairplayservice.model;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

        return Member.builder()
                .uid(memberEntity.getUid())
                .id(memberEntity.getId())
                .password(memberEntity.getPassword())
                .name(memberEntity.getName())
                .teamList(memberEntity.getTeamEntityList().stream().map(Team::from).collect(Collectors.toList()))
                .birthday(memberEntity.getBirthday())
                .address(memberEntity.getAddress())
                .phoneNumber(memberEntity.getPhoneNumber())
                .preferPosition(Position.from(memberEntity.getPreferPosition()))
                .level(memberEntity.getLevel())
                .phoneNumberDisclosureOption(memberEntity.getPhoneNumberDisclosureOption())
                .joinDate(memberEntity.getJoinDate())
                .recentLoginDate(memberEntity.getRecentLoginDate())
                .build();
    }

    public MemberEntity toMemberEntity() {

        return MemberEntity.builder()
                .uid(getUid())
                .id(getId())
                .password(getPassword())
                .name(getName())
                .teamEntityList(getTeamList().stream().map(Team::toTeamEntity).collect(Collectors.toList()))
                .birthday(getBirthday())
                .address(getAddress())
                .phoneNumber(getPhoneNumber())
                .preferPosition(getPreferPosition().getPosition())
                .level(getLevel())
                .phoneNumberDisclosureOption(getPhoneNumberDisclosureOption())
                .joinDate(getJoinDate())
                .recentLoginDate(getRecentLoginDate())
                .build();
    }

}
