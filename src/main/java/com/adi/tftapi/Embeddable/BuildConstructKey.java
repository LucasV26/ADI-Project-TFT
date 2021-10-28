package com.adi.tftapi.Embeddable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class BuildConstructKey implements Serializable {

    @Column(name = "build_id")
    private Long buildId;

    @Column(name = "champion_id")
    private String championId;
}
