package com.example.pfairplayservice.cassandra.model;

import com.example.pfairplayservice.cassandra.pk.CTeamPK;
import com.example.pfairplayservice.cassandra.udtModel.CMemberEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Builder
@Table("team")
public class CTeamEntity {

    @PrimaryKey
    private CTeamPK cTPK;

    @Column("team_name")
    private String teamName;

    @Column("level")
    private int level;

    @Column("team_lead_member")
    private CMemberEntity teamLeadMember;

    @Column("registration_date")
    private Date registrationDate;

    @Column("found_date")
    private Date foundDate;

    @Column("match_count")
    private int matchCount;

    @Column("rating")
    private int rating;

}
