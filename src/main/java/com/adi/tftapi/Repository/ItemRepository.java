package com.adi.tftapi.Repository;

import com.adi.tftapi.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
