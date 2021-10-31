package com.adi.tftapi.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "championId")
public class Unidade implements Serializable {

    @Id
    private String championId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    @ManyToMany
    @JoinTable(name = "unidade_sins", joinColumns = @JoinColumn(name = "id_unidade"), inverseJoinColumns = @JoinColumn(name = "id_sinergia"))
    private List<Sinergia> traits;

    @OneToMany(mappedBy = "unidade")
    private List<BuildConstruct> buildConstructs;
}
