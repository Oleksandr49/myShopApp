package com.example.firstproject.repository;

import com.example.firstproject.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
