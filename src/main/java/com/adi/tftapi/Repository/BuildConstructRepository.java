package com.adi.tftapi.Repository;

import com.adi.tftapi.Embeddable.BuildConstructKey;
import com.adi.tftapi.Entity.BuildConstruct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildConstructRepository extends JpaRepository<BuildConstruct, BuildConstructKey> {

    List<BuildConstruct> findByIdBuild(int build);

    List<BuildConstruct> findByIdChampion(String champion);
}
