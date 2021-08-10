package model.get;

import mysql.model.PlayGroundEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayGroundForGet {

    private Integer playGroundNo;

    private String name;

    private String mainAddress;

    private String subAddress;

    public static PlayGroundForGet from(PlayGroundEntity playGroundEntity) {

        return PlayGroundForGet.builder()
                .playGroundNo(playGroundEntity.getPlayGroundNo())
                .name(playGroundEntity.getName())
                .mainAddress(playGroundEntity.getMainAddress())
                .subAddress(playGroundEntity.getSubAddress())
                .build();

    }

    public PlayGroundForGet summarizeThis() {

        return PlayGroundForGet.builder()
                .playGroundNo(playGroundNo)
                .name(name)
                .mainAddress(null)
                .subAddress(null)
                .build();

    }

}
