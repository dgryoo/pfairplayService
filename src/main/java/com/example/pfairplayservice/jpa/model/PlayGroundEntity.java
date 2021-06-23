package com.example.pfairplayservice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "play_ground")
public class PlayGroundEntity {

    @Id
    private Integer playGroundNo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mainAddress;

    @Column(nullable = false)
    private String subAddress;

}
