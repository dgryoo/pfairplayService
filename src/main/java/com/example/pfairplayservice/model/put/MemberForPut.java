package com.example.pfairplayservice.model.put;

import com.example.pfairplayservice.model.enumfield.DisClosureOption;
import com.example.pfairplayservice.model.enumfield.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberForPut {

    private String address;

    private String phoneNumber;

    private Position preferPosition;

    private int level;

    private DisClosureOption phoneNumberDisclosureOption;

}
