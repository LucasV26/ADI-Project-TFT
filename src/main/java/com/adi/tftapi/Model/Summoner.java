package com.adi.tftapi.Model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Summoner implements Serializable {

    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private int profileIconId;
    private long revisionDate;
    private int summonerLevel;
}
