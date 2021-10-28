package com.adi.tftapi.Entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Unidade implements Serializable {

    @Id
    private String championId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    @OneToMany(mappedBy = "unidade")
    private List<BuildConstruct> buildConstructs;
}
