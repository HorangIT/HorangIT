package com.a101.ssafy.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.a101.ssafy.project.model.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	Page<Item>findAll(Specification<Item> spec, Pageable pageable);
	List<Item>findAll(Specification<Item> spec);
	Page<Item> findByName(String name, Pageable pageable);
	Optional<Item> findById(Long id);
}
