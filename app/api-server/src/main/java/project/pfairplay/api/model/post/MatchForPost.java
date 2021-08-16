package project.pfairplay.api.model.post;

import lombok.Builder;
import lombok.Data;
import project.pfairplay.storage.mysql.model.MatchEntity;
import project.pfairplay.storage.mysql.model.PlayGroundEntity;
import project.pfairplay.storage.mysql.model.TeamEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class MatchForPost {

    @NotNull(message = "PGN01")
    @Min(value = 1, message = "PGN02")
    private Integer playGroundNo;

    @NotNull(message = "PRICE01")
    private Integer price;

    @NotBlank(message = "OTT01")
    private String ownerTeamTid;

    @NotNull(message = "SD01")
    private Date startDate;

    @NotNull(message = "ED01")
    private Date endDate;

    @NotNull(message = "MSG01")
    @Size(min = 1, max = 255, message = "MSG02")
    private String message;

    public MatchEntity toMatchEntity(TeamEntity ownerTeam, PlayGroundEntity playGroundEntity) {

        return MatchEntity.builder()
                .playGround(playGroundEntity)
                .price(price)
                .ownerTeam(ownerTeam)
                .startDate(startDate)
                .endDate(endDate)
                .message(message)
                .registrationDate(new Date())
                .viewCount(0)
                .status(0)
                .build();

    }
}
