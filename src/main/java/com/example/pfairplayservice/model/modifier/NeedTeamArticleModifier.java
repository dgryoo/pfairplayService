package com.example.pfairplayservice.model.modifier;

import com.example.pfairplayservice.model.enumfield.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedTeamArticleModifier {

    private Member writeMember;

    private String subject;

    private String detail;

    private Position needPosition;


}
