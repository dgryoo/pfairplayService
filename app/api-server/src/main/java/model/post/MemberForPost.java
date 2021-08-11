package model.post;

import mysql.model.MemberEntity;
import model.enumfield.DisClosureOption;
import model.enumfield.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberForPost {

    private String id;

    private String password;

    private String name;

    private Date birthday;

    private String address;

    private String phoneNumber;

    private Position preferPosition;

    private int level;

    private DisClosureOption phoneNumberDisclosureOption;

    public MemberEntity toMemberEntity() {

        return MemberEntity.builder()
                .id(getId())
                .password(getPassword())
                .name(getName())
                .birthday(getBirthday())
                .address(getAddress())
                .phoneNumber(getPhoneNumber())
                .preferPosition(getPreferPosition().getPosition())
                .level(getLevel())
                .phoneNumberDisclosureOption(getPhoneNumberDisclosureOption().getDisclosureOption())
                .joinDate(new Date())
                .recentLoginDate(new Date())
                .build();
    }
}
