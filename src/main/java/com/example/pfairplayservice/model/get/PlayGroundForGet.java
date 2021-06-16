package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.PlayGroundEntity;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PlayGroundForGet {

    private int playGroundNo;

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

}
