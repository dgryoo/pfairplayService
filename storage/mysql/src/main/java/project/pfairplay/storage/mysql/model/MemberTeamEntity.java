package project.pfairplay.storage.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.storage.mysql.id.MemberTeamId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_team")
public class MemberTeamEntity {

    @EmbeddedId
    private MemberTeamId memberTeamId;

}