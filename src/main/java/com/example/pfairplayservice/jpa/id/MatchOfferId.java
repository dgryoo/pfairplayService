package com.example.pfairplayservice.jpa.id;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MatchOfferId implements Serializable {

    @EqualsAndHashCode.Include
    private int mid;

    @EqualsAndHashCode.Include
    private int oid;

}
