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

    private int gold_left;
    private int last_round;
    private int level;
    private int placement;
    private int players_eliminated;
    private String puuid;
    private float time_eliminated;
    private int total_damage_to_players;
    private List<Trait> traits;
    private List<Unit> units;
}
