package com.adi.tftapi.Entity;

import com.adi.tftapi.Embeddable.BuildConstructKey;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BuildConstruct implements Serializable {

    @EmbeddedId
    private BuildConstructKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("buildId")
    @JoinColumn(name = "build_id")
    private Build build;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("championId")
    @JoinColumn(name = "champion_id")
    private Unidade unidade;

    @ManyToMany
    @JoinTable(name = "item_champ_build", joinColumns = @JoinColumn(name = "build_champ_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;
}
