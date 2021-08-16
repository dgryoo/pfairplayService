package project.pfairplay.storage.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(generator = "member-uid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "member-uid", strategy = "uuid")
    private String uid;

    @Column(nullable = false)
    @Length(min = 6, max = 10)
    private String id;

    @Column(nullable = false)
    @Length(min = 6, max = 10)
    private String password;

    @Column(nullable = false)
    @Length(min = 2, max = 10)
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(length = 1)
    private int preferPosition;

    @Column(length = 1)
    @ColumnDefault("1")
    private int level;

    @Column(nullable = false, length = 1)
    @ColumnDefault("1")
    private int phoneNumberDisclosureOption;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joinDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date recentLoginDate;

}