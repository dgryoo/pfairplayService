package com.example.pfairplayservice.common.filter;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;

public class FilterManager {

    public static MemberEntity teamLeadMemberFilter(MemberEntity memberEntity) {

        return MemberEntity.builder()
                .name(memberEntity.getName())
                .phoneNumber(memberEntity.getPhoneNumber())
                .build();

    }


    public static MemberEntity teamMemberFilter(MemberEntity memberEntity) {

        return MemberEntity.builder()
                .name(memberEntity.getName())
                .birthday(memberEntity.getBirthday())
                .address(memberEntity.getAddress())
                .preferPosition(memberEntity.getPreferPosition())
                .level(memberEntity.getLevel())
                .build();
    }

    public static TeamEntity teamMemberFilter(TeamEntity teamEntity) {

        return TeamEntity.builder()
                .teamName(teamEntity.getTeamName())
                .teamLeadMember(teamLeadMemberFilter(teamEntity.getTeamLeadMember()))
                .build();
    }

}
