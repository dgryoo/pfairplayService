package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.id.MemberTeamId;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.util.Date;

public class TestEntityGenerator {

    public static MemberEntity generateMemberEntity() {

        String randomIdString = RandomString.make(10);

        return MemberEntity
                .builder()
                .id(randomIdString)
                .password("password")
                .address("address")
                .birthday(new Date())
                .joinDate(new Date())
                .level(1)
                .preferPosition(1)
                .phoneNumber("010-0000-0000")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(1)
                .name("name")
                .build();
    }

    public static TeamEntity generateTeamEntity(MemberEntity memberEntity) {

        String randomTeamNameString = RandomString.make(10);

        return TeamEntity
                .builder()
                .teamName(randomTeamNameString)
                .teamLeadMember(memberEntity)
                .activityAreaAddress("activityAreaAddress")
                .registrationDate(new Date())
                .foundDate(new Date())
                .build();
    }

    public static MemberTeamEntity generateMemberTeamEntity(String uid, String tid) {

        MemberTeamId memberTeamId = MemberTeamId
                .builder()
                .uid(uid)
                .tid(tid)
                .build();

        return MemberTeamEntity
                .builder()
                .memberTeamId(memberTeamId)
                .build();
    }

}
