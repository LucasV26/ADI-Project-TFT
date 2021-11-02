package com.adi.tftapi.Entity;

import com.adi.tftapi.Embeddable.BuildConstructKey;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
        property = "id")
public class BuildConstruct implements Serializable {

    @EmbeddedId
    private BuildConstructKey id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("build")
    @JoinColumn(name = "build_id")
    private Build build;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("champion")
    @JoinColumn(name = "champion_id")
    private Unidade unidade;

    @ManyToMany
    @JoinTable(name = "item_champ_build", joinColumns = {@JoinColumn(name = "build_id"), @JoinColumn(name = "champion_id")}, inverseJoinColumns = @JoinColumn(name = "item_id"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Item> items;
}
