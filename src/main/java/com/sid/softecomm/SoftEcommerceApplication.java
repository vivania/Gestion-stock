package com.sid.softecomm;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.SpringServletContainerInitializer;

import com.sid.softecomm.dao.CategoryRepository;
import com.sid.softecomm.dao.ProductRepository;
import com.sid.softecomm.entities.Category;
import com.sid.softecomm.entities.Product;

import net.bytebuddy.utility.RandomString;

@SpringBootApplication
public class SoftEcommerceApplication extends SpringServletContainerInitializer implements CommandLineRunner {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	RepositoryRestConfiguration repositoryRestConfiguration;
	
	
	public static void main(String[] args) {
		SpringApplication.run(SoftEcommerceApplication.class, args);
	}
	
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(SoftEcommerceApplication.class);
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		
		repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);
		
		Random rnd = new Random();
		
		categoryRepository.save(new Category(new Long(0), "Echarpe Soie", null, "", null));
		categoryRepository.save(new Category(new Long(0), "Echarpe Chachemire", null, "",  null));
		categoryRepository.save(new Category(new Long(0), "Echarpe Coton", null, "", null));
		
		categoryRepository.findAll().forEach(c -> {
			
			for(int i = 0; i < 4; i++) {
				Product product = new Product();
				product.setName(RandomString.make(10));
				product.setDescription(RandomString.make(15));
				product.setPrice(10 + rnd.nextInt(1000));
				product.setPromotion(rnd.nextBoolean());
				product.setAvailable(rnd.nextBoolean());
				product.setSelected(rnd.nextBoolean());
				product.setPhotoName("echarpe.png");
				product.setCategory(c);
				
				productRepository.save(product);
			}
		});
		
	}

}
