package com.adi.tftapi.Repository;

import com.adi.tftapi.Entity.Build;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildRepository extends JpaRepository<Build, Integer> {
}
