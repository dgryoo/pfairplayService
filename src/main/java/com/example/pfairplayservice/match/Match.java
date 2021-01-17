package com.example.pfairplayservice.match;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {


    private TeamEntity ownerTeam;
    private TeamEntity guestTeam;
    private Timestamp matchStartTime;
    private Timestamp matchEndTime;
    private Integer price;
    private String description;
    private boolean status;


}
