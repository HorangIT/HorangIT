package com.a101.ssafy.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.model.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
