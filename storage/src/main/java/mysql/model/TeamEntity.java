package mysql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(generator = "team-tid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "team-tid", strategy = "uuid")
    private String tid;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private int level;

    @OneToOne
    private MemberEntity teamLeadMember;

    @Column(nullable = false)
    private String activityAreaAddress;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date foundDate;

    private int recommendCount;

    private int matchCount;

    private int rating;

}
