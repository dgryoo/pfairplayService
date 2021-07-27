package com.example.pfairplayservice.cassandra.udtModel;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Date;

@Data
@Builder
@UserDefinedType(value = "team_lead_member_type")
public class CMemberEntity {

    private String uid;

    private String id;

    private String password;

    private String name;

    private Date birthday;

    private String address;

    private String phone_number;

    private int prefer_position;

    private int level;

    private int phone_number_disclosure_option;

    private Date join_date;

    private Date recent_login_Date;

    public static CMemberEntity from(MemberEntity memberEntity) {

        return CMemberEntity.builder()
                .uid(memberEntity.getUid())
                .id(memberEntity.getId())
                .password(memberEntity.getPassword())
                .name(memberEntity.getName())
//                .birthday(memberEntity.getBirthday())
                .birthday(null)
                .address(memberEntity.getAddress())
                .phone_number(memberEntity.getPhoneNumber())
                .prefer_position(memberEntity.getPreferPosition())
                .level(memberEntity.getLevel())
                .phone_number_disclosure_option(memberEntity.getPhoneNumberDisclosureOption())
//                .join_date(memberEntity.getJoinDate())
//                .recent_login_Date(memberEntity.getRecentLoginDate())
                .join_date(null)
                .recent_login_Date(null)
                .build();

    }
}
