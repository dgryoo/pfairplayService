package com.example.pfairplayservice.model.put;

import com.example.pfairplayservice.model.enumfield.PlayGround;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class  MatchForPut {

    private PlayGround playGround;

    private int price;

    private Date startDate;

    private Date endDate;

    private String message;



}
