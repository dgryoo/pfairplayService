package project.pfairplay.api.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.storage.mysql.model.MemberEntity;
import project.pfairplay.storage.mysql.model.TeamEntity;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamForPost {

    private String teamName;

    private String teamLeadMemberUid;

    private int level;

    private String activityAreaAddress;

    private Date foundDate;

    public TeamEntity toTeamEntity(MemberEntity memberEntity) {

        return TeamEntity.builder()
                .teamName(getTeamName())
                .level(1)
                .teamLeadMember(memberEntity)
                .activityAreaAddress(getActivityAreaAddress())
                .registrationDate(new Date())
                .foundDate(getFoundDate())
                .recommendCount(0)
                .matchCount(0)
                .rating(level * 200)
                .build();
    }

}
