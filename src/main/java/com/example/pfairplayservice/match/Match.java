package com.example.pfairplayservice.match;

import com.example.pfairplayservice.team.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {


    private Team ownerTeam;
    private Team guestTeam;
    private Timestamp matchStartTime;
    private Timestamp matchEndTime;
    private Integer price;


}
