package com.example.pfairplayservice;

import com.example.pfairplayservice.jpa.id.MemberTeamId;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.model.enumfield.DisClosureOption;
import com.example.pfairplayservice.model.enumfield.Position;
import com.example.pfairplayservice.model.origin.Member;
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
                .phoneNumber("01000000000")
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

    public static Member generateMember() {

        String randomIdString = RandomString.make(6);

        return Member
                .builder()
                .id(randomIdString)
                .password("password")
                .name("이름테스트")
                .birthday(new Date())
                .address("주소테스트")
                .phoneNumber("01054376283")
                .preferPosition(Position.NONE)
                .level(0)
                .phoneNumberDisclosureOption(DisClosureOption.ALL)
                .build();
    }

}
