package com.example.pfairplayservice.jpa.model;

import com.example.pfairplayservice.jpa.id.MemberTeamId;
import lombok.*;

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