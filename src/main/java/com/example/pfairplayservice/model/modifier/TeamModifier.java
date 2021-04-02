package com.example.pfairplayservice.model.modifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamModifier {

    private String teamName;

    private String activityAreaAddress;

    private Date foundDate;

}
