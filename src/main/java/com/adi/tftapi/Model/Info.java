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

    private String game_version;
    private List<Participant> participants;
    private int tft_set_number;
}
