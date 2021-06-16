package com.example.pfairplayservice.model.put;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MatchForPut {

    private String ownerTeamTid;

    private int playGroundNo;

    private int price;

    private Date startDate;

    private Date endDate;

    private String message;


}
