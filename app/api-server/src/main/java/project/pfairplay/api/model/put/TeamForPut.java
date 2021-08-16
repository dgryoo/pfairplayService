package project.pfairplay.api.model.put;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamForPut {

    private String teamName;

    private String activityAreaAddress;

    private Date foundDate;

}
