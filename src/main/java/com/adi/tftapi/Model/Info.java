package com.adi.tftapi.Model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Info {

    private long gamte_datetime;
    private float game_length;
    private String game_version;
    private List<Participant> participants;
    private int queue_id;
    private int tft_set_number;
}
