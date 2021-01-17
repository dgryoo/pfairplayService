package com.example.pfairplayservice.common.filter;

import com.example.pfairplayservice.model.Member;

public class FilterManager {

    public static Member teamLeadMemberFilter(Member member) {

        Member filterMember = new Member(member.getName(), member.getPhoneNumber());

        return filterMember;

    }

}
