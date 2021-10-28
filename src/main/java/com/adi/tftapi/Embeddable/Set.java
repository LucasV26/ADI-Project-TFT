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
public class Set implements Serializable {

    @Column(nullable = false)
    private String style;

    @Column(nullable = false)
    private int min;

    @Column(nullable = false)
    private int max;
}
