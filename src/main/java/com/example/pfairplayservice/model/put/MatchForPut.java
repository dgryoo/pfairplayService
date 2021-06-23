package com.example.pfairplayservice.model.put;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class MatchForPut {

    @NotNull
    @Min(value = 1, message = "PlayGroundNo must be greater or equal 1")
    private Integer playGroundNo;

    @NotNull
    @Min(value = 0, message = "Price must be greater or equal 0")
    private Integer price;

    @NotNull
    @NotBlank
    private String ownerTeamTid;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String message;


}
