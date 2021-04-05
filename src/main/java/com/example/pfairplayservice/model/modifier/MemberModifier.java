package com.example.pfairplayservice.model.modifier;

import com.example.pfairplayservice.model.origin.DisClosureOption;
import com.example.pfairplayservice.model.origin.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifier {

    private String address;

    private String phoneNumber;

    private Position preferPosition;

    private int level;

    private DisClosureOption phoneNumberDisclosureOption;

}
