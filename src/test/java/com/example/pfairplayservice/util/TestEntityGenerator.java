package com.example.pfairplayservice.util;

import com.example.pfairplayservice.jpa.id.MemberTeamId;
import com.example.pfairplayservice.jpa.model.*;
import com.example.pfairplayservice.model.enumfield.DisClosureOption;
import com.example.pfairplayservice.model.enumfield.PlayGround;
import com.example.pfairplayservice.model.enumfield.Position;
import com.example.pfairplayservice.model.post.*;
import com.example.pfairplayservice.model.put.MatchForPut;
import com.example.pfairplayservice.model.put.MemberForPut;
import com.example.pfairplayservice.model.put.NeedTeamArticleForPut;
import com.example.pfairplayservice.model.put.TeamForPut;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.util.Date;

public class TestEntityGenerator {

    public static MemberEntity generateMemberEntity() {

        String randomIdString = RandomString.make(8);

        return MemberEntity
                .builder()
                .id(randomIdString)
                .password("givenPw")
                .name("부여받은이름")
                .address("부여받은주소")
                .birthday(new Date())
                .joinDate(new Date())
                .level(0)
                .preferPosition(0)
                .phoneNumber("01011111111")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(0)
                .build();
    }

    public static TeamEntity generateTeamEntity(MemberEntity memberEntity) {

        String randomTeamNameString = RandomString.make(8);

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

    public static MemberForPost generateMemberForPost() {

        String randomTeamNameString = RandomString.make(8);

        return MemberForPost.builder()
                .id(randomTeamNameString)
                .password("givenPw")
                .name("부여받은이름")
                .birthday(new Date())
                .address("부여받은주소")
                .phoneNumber("01011111111")
                .preferPosition(Position.NONE)
                .level(0)
                .phoneNumberDisclosureOption(DisClosureOption.ALL)
                .build();

    }

    public static MemberForPut generateMemberForPut() {

        return MemberForPut.builder()
                .address("수정된주소")
                .phoneNumber("01022222222")
                .preferPosition(Position.FW)
                .level(1)
                .phoneNumberDisclosureOption(DisClosureOption.NOBODY)
                .build();
    }

    public static TeamForPost generateTeamForPut(String memberUid) {

        return TeamForPost.builder()
                .teamName("부여받은팀이름")
                .teamLeadMemberUid(memberUid)
                .activityAreaAddress("부여받은주소")
                .foundDate(new Date())
                .build();
    }

    public static TeamForPut generateTeamForPut() {

        return TeamForPut.builder()
                .teamName("부여받은팀이름")
                .activityAreaAddress("부여받은주소")
                .foundDate(new Date())
                .build();
    }

    public static MemberTeamId generateMemberTeamId(String uid, String tid) {
        return MemberTeamId.builder()
                .uid(uid)
                .tid(tid)
                .build();
    }

    public static NeedTeamArticleEntity generateNeedTeamArticleEntity(MemberEntity memberEntity) {

        return NeedTeamArticleEntity.builder()
                .writeMember(memberEntity)
                .subject("givenSubject")
                .detail("givenDetail")
                .needPosition(0)
                .writeDate(new Date())
                .modifiedDate(new Date())
                .viewCount(0)
                .status(0)
                .build();
    }

    public static NeedTeamArticleForPost generateNeedTeamArticleForPost(String uid) {

        return NeedTeamArticleForPost.builder()
                .writeMemberUid(uid)
                .subject("givenSubject")
                .detail("givenDetail")
                .needPosition(Position.NONE)
                .build();
    }

    public static NeedTeamArticleForPut generateNeedTeamArticleForPut(String uid) {

        return NeedTeamArticleForPut.builder()
                .writeMemberUid(uid)
                .subject("givenSubject")
                .detail("givenDetail")
                .needPosition(Position.NONE)
                .build();
    }

    public static MatchForPost generateMatchForPost(String tid) {

        return MatchForPost.builder()
                .playGround(PlayGround.옥녀봉)
                .ownerTeamTid(tid)
                .startDate(new Date())
                .endDate(new Date())
                .message("test message")
                .build();
    }

    public static MatchEntity generateMatchEntity(TeamEntity givenTeamEntity) {

        return MatchEntity.builder()
                .groundNumber(0)
                .price(5000)
                .ownerTeam(givenTeamEntity)
                .startDate(new Date())
                .endDate(new Date())
                .message("given message")
                .registrationDate(new Date())
                .viewCount(0)
                .status(0)
                .build();

    }

    public static MatchForPut generateMatchForPut(String tid) {

        return MatchForPut.builder()
                .ownerTeamTid(tid)
                .playGround(PlayGround.을미기)
                .price(7777)
                .startDate(new Date())
                .endDate(new Date())
                .message("given message")
                .build();
    }

    public static OfferForPost generateOfferForPost(int targetMatchNo, String sandTeamTid, String receiveTeamTid) {

        return OfferForPost.builder()
                .targetMatchNo(targetMatchNo)
                .sandTeamTid(sandTeamTid)
                .receiveTeamTid(receiveTeamTid)
                .message("given message")
                .build();

    }

    public static OfferEntity generateOfferEntity(MatchEntity targetMatch, TeamEntity sandTeam, TeamEntity receiveTeam) {

        return OfferEntity.builder()
                .targetMatch(targetMatch)
                .sandTeam(sandTeam)
                .receiveTeam(receiveTeam)
                .message("given message")
                .offerDate(new Date())
                .offerStatus(0)
                .build();
    }
}
