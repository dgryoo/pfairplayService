package com.example.pfairplayservice.jpa.id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class MemberTeamId implements Serializable {


    @EqualsAndHashCode.Include
    private String uid;

    @EqualsAndHashCode.Include
    private String tid;


}
