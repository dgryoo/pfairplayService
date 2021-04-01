package com.example.pfairplayservice.model.modifier;

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

    private int preferPosition;

    private int level;

    private int phoneNumberDisclosureOption;

}
