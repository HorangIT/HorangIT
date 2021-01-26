package com.a101.ssafy.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a101.ssafy.project.image.Image;
import com.a101.ssafy.project.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService{
	ImageRepository imageRepository;
	
	@Autowired
	public void setImageRepository(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
	
	@Override
	public boolean registerImage(Image image) {
		imageRepository.save(image);
		
		return true;
	}

}
