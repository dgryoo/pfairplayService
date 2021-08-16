package project.pfairplay.api.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.api.model.enumfield.DisClosureOption;
import project.pfairplay.api.model.enumfield.Position;
import project.pfairplay.storage.mysql.model.MemberEntity;

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
