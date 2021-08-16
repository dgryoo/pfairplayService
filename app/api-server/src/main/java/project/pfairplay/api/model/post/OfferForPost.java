package project.pfairplay.api.model.post;

import lombok.Builder;
import lombok.Data;
import project.pfairplay.storage.mysql.model.MatchEntity;
import project.pfairplay.storage.mysql.model.OfferEntity;
import project.pfairplay.storage.mysql.model.TeamEntity;

import java.util.Date;

@Data
@Builder
public class OfferForPost {

    private int targetMatchNo;

    private String sandTeamTid;

    private String receiveTeamTid;

    private String message;

    public OfferEntity toOfferEntity(MatchEntity targetMatch, TeamEntity sandTeam, TeamEntity receiveTeam) {

        return OfferEntity.builder()
                .targetMatch(targetMatch)
                .sandTeam(sandTeam)
                .receiveTeam(receiveTeam)
                .message(message)
                .offerDate(new Date())
                .offerStatus(0)
                .build();
    }

}
