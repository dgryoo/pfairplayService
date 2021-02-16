package com.example.pfairplayservice.jpa.id;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MemberTeamId implements Serializable {


    @EqualsAndHashCode.Include
    private String uid;

    @EqualsAndHashCode.Include
    private String tid;


}
