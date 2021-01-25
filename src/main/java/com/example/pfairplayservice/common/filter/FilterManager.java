package com.example.pfairplayservice.common.filter;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.model.Member;

public class FilterManager {

    public static MemberEntity teamLeadMemberFilter(MemberEntity memberEntity) {

        return MemberEntity.builder()
                .name(memberEntity.getName())
                .phoneNumber(memberEntity.getPhoneNumber())
                .build();

    }

}
