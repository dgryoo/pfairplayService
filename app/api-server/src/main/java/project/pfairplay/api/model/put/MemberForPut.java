package project.pfairplay.api.model.put;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.pfairplay.api.model.enumfield.DisClosureOption;
import project.pfairplay.api.model.enumfield.Position;

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
