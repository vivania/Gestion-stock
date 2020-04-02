package com.sid.softecomm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sid.softecomm.dao.ProductRepository;
import com.sid.softecomm.entities.Product;

@CrossOrigin("*")
@RestController
public class CatalogueController {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping(path = "/productPhoto/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getProductPhoto(@PathVariable("id") Long id) throws IOException {
		
		Product product = productRepository.findById(id).get();
		
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Desktop/photo-test/" + product.getPhotoName()));
	}
	
	
	@PostMapping(path = "/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file, @PathVariable long id) throws IOException {
		
		Product product = productRepository.findById(id).get();
		
		product.setPhotoName(file.getOriginalFilename());
		
		Files.write(Paths.get(System.getProperty("user.home") + "/Desktop/photo-test/" + product.getPhotoName()), file.getBytes());
		
		productRepository.save(product);
	}
	

}
