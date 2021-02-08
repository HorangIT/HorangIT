package com.a101.ssafy.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.model.item.Item;

public interface SearchRepository extends JpaRepository<Item, Long>{
	Page<Item>findAll(Specification<Item> spec, Pageable pageable);
	List<Item>findAll(Specification<Item> spec);

}
