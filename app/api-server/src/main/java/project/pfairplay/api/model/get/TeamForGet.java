package project.pfairplay.api.model.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.storage.mysql.model.TeamEntity;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamForGet {

    private String tid;

    private String teamName;

    private Integer level;

    private MemberForGet teamLeadMember;

    private String activityAreaAddress;

    private Date registrationDate;

    private Date foundDate;

    public static TeamForGet from(TeamEntity teamEntity) {
        if (teamEntity != null) {
            return TeamForGet.builder()
                    .tid(teamEntity.getTid())
                    .teamName(teamEntity.getTeamName())
                    .level(teamEntity.getLevel())
                    .teamLeadMember(MemberForGet.from(teamEntity.getTeamLeadMember()))
                    .activityAreaAddress(teamEntity.getActivityAreaAddress())
                    .registrationDate(teamEntity.getRegistrationDate())
                    .foundDate(teamEntity.getFoundDate())
                    .build();
        }

        return null;

    }

    public TeamForGet summarizeThis() {
        return TeamForGet.builder()
                .tid(tid)
                .teamName(teamName)
                .level(level)
                .teamLeadMember(null)
                .activityAreaAddress(null)
                .registrationDate(null)
                .foundDate(null)
                .build();
    }

}
