package com.adi.tftapi.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Unidade implements Serializable {

    public Unidade(String id){
        this.championId = id;
    }

    @Id
    private String championId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "unidade_sins", joinColumns = @JoinColumn(name = "id_unidade"), inverseJoinColumns = @JoinColumn(name = "id_sinergia"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Sinergia> traits;

    @OneToMany(mappedBy = "unidade", cascade = CascadeType.REMOVE)
    @JsonIdentityReference(alwaysAsId = true)
    private List<BuildConstruct> buildConstructs;
}
