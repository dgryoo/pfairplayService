package project.pfairplay.api.model.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.api.model.enumfield.OfferStatus;
import project.pfairplay.storage.mysql.model.OfferEntity;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferForGet {

    private int offerNo;

    private MatchForGet targetMatch;

    private TeamForGet sander;

    private TeamForGet receiver;

    private Date offerDate;

    private OfferStatus offerStatus;

    public static OfferForGet from(OfferEntity offerEntity) {

        return OfferForGet.builder()
                .offerNo(offerEntity.getOfferNo())
                .targetMatch(MatchForGet.from(offerEntity.getTargetMatch()))
                .sander(TeamForGet.from(offerEntity.getSandTeam()))
                .receiver(TeamForGet.from(offerEntity.getReceiveTeam()))
                .offerDate(offerEntity.getOfferDate())
                .offerStatus(OfferStatus.from(offerEntity.getOfferStatus()))
                .build();

    }
}
