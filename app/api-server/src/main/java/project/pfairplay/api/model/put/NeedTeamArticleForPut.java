package project.pfairplay.api.model.put;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.api.model.enumfield.Position;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedTeamArticleForPut {

    private String writeMemberUid;

    private String subject;

    private String detail;

    private Position needPosition;


}
