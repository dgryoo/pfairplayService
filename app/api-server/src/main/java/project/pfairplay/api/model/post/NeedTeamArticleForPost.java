package project.pfairplay.api.model.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.api.model.enumfield.Position;
import project.pfairplay.api.model.enumfield.Status;
import project.pfairplay.storage.mysql.model.MemberEntity;
import project.pfairplay.storage.mysql.model.NeedTeamArticleEntity;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedTeamArticleForPost {

    private String writeMemberUid;

    private String subject;

    private String detail;

    private Position needPosition;

    public NeedTeamArticleEntity toNeedTeamArticleEntity(MemberEntity writeMember) {

        return NeedTeamArticleEntity.builder()
                .writeMember(writeMember)
                .subject(subject)
                .detail(detail)
                .needPosition(needPosition.getPosition())
                .writeDate(new Date())
                .modifiedDate(new Date())
                .viewCount(0)
                .status(Status.ONGOING.getStatus())
                .build();

    }

}
