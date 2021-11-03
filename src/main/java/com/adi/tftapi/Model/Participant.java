package com.adi.tftapi.Model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Participant {

    private int placement;
    private String puuid;
    private List<Unit> units;
}
