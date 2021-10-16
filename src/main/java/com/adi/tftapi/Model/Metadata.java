package com.adi.tftapi.Model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Metadata {

    private String data_version;
    private String match_id;
    private List<String> participants;
}
