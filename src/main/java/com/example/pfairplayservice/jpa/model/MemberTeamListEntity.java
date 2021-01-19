package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
// TODO EmbeddedID
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_team_list")
public class MemberTeamListEntity {

    private String tid;

    private String uid;

}
