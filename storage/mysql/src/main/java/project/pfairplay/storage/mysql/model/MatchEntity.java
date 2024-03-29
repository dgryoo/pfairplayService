package project.pfairplay.storage.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "soccer_match")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int matchNo;

    @Column(nullable = false)
    private int price;

    @OneToOne
    @JoinColumn(name = "play_ground_no")
    private PlayGroundEntity playGround;

    @OneToOne
    private TeamEntity ownerTeam;

    @OneToOne
    private TeamEntity guestTeam;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int status;

    private Integer ownerScore;

    private Integer guestScore;

}
