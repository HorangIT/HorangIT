package com.a101.ssafy.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.model.image.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
