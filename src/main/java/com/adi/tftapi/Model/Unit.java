package com.adi.tftapi.Model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Unit {

    private String character_id;
    private List<Integer> items;
    private String name;
    private int rarity;
    private int tier;
}
