package model.put;

import model.enumfield.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
