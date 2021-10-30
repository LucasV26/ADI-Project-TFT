package com.adi.tftapi.Entity;

import com.adi.tftapi.Embeddable.Set;
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
@ToString
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "key")
public class Sinergia implements Serializable {

    @Id
    @Column(name = "id")
    private String key;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String type;

    private String innate;

    @ElementCollection
    @CollectionTable(name = "trait_sets", joinColumns = @JoinColumn(name = "trait_key"), foreignKey = @ForeignKey(name = "fk_trait_set"))
    private List<Set> sets;
}
