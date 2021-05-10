package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.model.enumfield.DisClosureOption;
import com.example.pfairplayservice.model.enumfield.Position;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberForGet {

    private String uid;

    private String id;

    private String name;

    private Date birthday;

    private String address;

    private String phoneNumber;

    private Position preferPosition;

    private int level;

    private DisClosureOption phoneNumberDisclosureOption;

    private Date joinDate;

    private Date recentLoginDate;

    public static MemberForGet from(MemberEntity memberEntity) {


        return MemberForGet.builder()
                .uid(memberEntity.getUid())
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .birthday(memberEntity.getBirthday())
                .address(memberEntity.getAddress())
                .phoneNumber(memberEntity.getPhoneNumber())
                .preferPosition(Position.from(memberEntity.getPreferPosition()))
                .level(memberEntity.getLevel())
                .phoneNumberDisclosureOption(DisClosureOption.from(memberEntity.getPhoneNumberDisclosureOption()))
                .joinDate(memberEntity.getJoinDate())
                .recentLoginDate(memberEntity.getRecentLoginDate())
                .build();
    }

}
