package com.adi.tftapi.Repository;

import com.adi.tftapi.Entity.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BuildRepository extends JpaRepository<Build, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Build b SET b.name = :nome, b.description = :description WHERE b.id = :id")
    void updateBuildById(String nome, String description, int id);

}
