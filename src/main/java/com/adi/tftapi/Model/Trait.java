package com.adi.tftapi.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Trait {

    private String name;
    private int num_units;
    private int style;
    private int tier_current;
    private int tier_total;
}
