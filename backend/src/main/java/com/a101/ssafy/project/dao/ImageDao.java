package com.a101.ssafy.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.image.Image;

public interface ImageDao extends JpaRepository<Image, Long>{

}
