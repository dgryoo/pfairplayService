package com.example.pfairplayservice.common.filter;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.OfferEntity;
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

    public static MemberEntity articleMemberFilter(MemberEntity memberEntity) {

        return MemberEntity.builder()
                .name(memberEntity.getName())
                .birthday(memberEntity.getBirthday())
                .address(memberEntity.getAddress())
                .phoneNumber(memberEntity.getPhoneNumber())
                .preferPosition(memberEntity.getPreferPosition())
                .level(memberEntity.getLevel())
                .phoneNumberDisclosureOption(memberEntity.getPhoneNumberDisclosureOption())
                .build();
    }

    public static TeamEntity teamMemberFilter(TeamEntity teamEntity) {

        return TeamEntity.builder()
                .teamName(teamEntity.getTeamName())
                .teamLeadMember(teamLeadMemberFilter(teamEntity.getTeamLeadMember()))
                .build();
    }

    public static OfferEntity offerFilter(OfferEntity offerEntity) {

        MatchEntity filteredTargetMatch = MatchEntity.builder()
                .matchNo(offerEntity.getTargetMatch().getMatchNo())
                .ownerTeam(offerEntity.getTargetMatch().getOwnerTeam())
                .startDate(offerEntity.getTargetMatch().getStartDate())
                .endDate(offerEntity.getTargetMatch().getEndDate())
                .status(offerEntity.getTargetMatch().getStatus())
                .build();

        TeamEntity filteredSandTeam = TeamEntity.builder()
                .tid(offerEntity.getSandTeam().getTid())
                .teamLeadMember(teamLeadMemberFilter(offerEntity.getSandTeam().getTeamLeadMember()))
                .build();

        TeamEntity filteredReceiveTeam = TeamEntity.builder()
                .tid(offerEntity.getReceiveTeam().getTid())
                .teamLeadMember(teamLeadMemberFilter(offerEntity.getReceiveTeam().getTeamLeadMember()))
                .build();

        return OfferEntity.builder()
                .offerNo(offerEntity.getOfferNo())
                .targetMatch(filteredTargetMatch)
                .sandTeam(filteredSandTeam)
                .receiveTeam(filteredReceiveTeam)
                .message(offerEntity.getMessage())
                .offerDate(offerEntity.getOfferDate())
                .offerStatus(offerEntity.getOfferStatus())
                .build();

    }

}
