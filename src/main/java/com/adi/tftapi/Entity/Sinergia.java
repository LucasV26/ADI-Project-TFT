package com.adi.tftapi.Entity;

import com.adi.tftapi.Embeddable.Set;
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
public class Sinergia implements Serializable {

    @Id
    private String key;

    @Column(nullable = false)
    private String name;

    private String innate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String type;

    @ElementCollection
    @CollectionTable(name = "trait_sets", joinColumns = @JoinColumn(name = "trait_key"), foreignKey = @ForeignKey(name = "fk_trait_set"))
    private List<Set> sets;
}
