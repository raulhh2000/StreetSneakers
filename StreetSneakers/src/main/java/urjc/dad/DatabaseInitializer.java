package urjc.dad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import urjc.dad.models.Admin;
import urjc.dad.models.Purchase;
import urjc.dad.models.Product;
import urjc.dad.models.Review;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.PurchaseRepository;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;
import urjc.dad.repositories.UserRepository;

@Component
public class DatabaseInitializer {

	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;


	@PostConstruct
	public void init() {
		
		Admin admin1= new Admin("Raul", "Heredia", "raulHeredia@gmail.com", "1234");
		Admin admin2= new Admin("Alberto", "Martin", "albertomartin@gmail.com", "4567");
		Admin admin3= new Admin("Fatima", "Smount", "FatimaSmount@gmail.com", "8901");
		adminRepository.save(admin1);
		adminRepository.save(admin2);
		adminRepository.save(admin3);
		
		User user1= new User("Heredia", "Raul", "HerediaRaul@gmail.com", "1234");
		User user2= new User("Martin","Alberto", "martinalberto@gmail.com", "4567");
		User user3= new User("Smount","Fatima","Smountfatima@gmail.com", "8901");
		
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		
		Product product1 = new Product("AirForce1", "Zapatillas grandes", 123.4, 44);
		Product product2 = new Product("Jordan", "Zapatillas de baloncesto", 99.99, 42);
		Product product3 = new Product("Ultraboost", "Zapatillas comodas", 180, 41);
		
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		
		user1.getWishList().add(product1);
		userRepository.save(user1);
		
		
		List<Product> list= new ArrayList<>();
		list.add(product1);
		list.add(product3);
		list.add(product2);
		LocalDateTime date =  LocalDateTime.of(2022,02,02,10,00,00);
		Purchase purchase1= new Purchase(user1,date,300.02,list);
		
		purchaseRepository.save(purchase1);
		
		//user1.getPurchases().add(purchase1);
		//userRepository.save(user1);
		
		Review review1= new Review(product1,"muy bonitas",user1,"perfectas",5);
		Review review2= new Review(product2,"muy grandes",user2,"mala talla",2);
		Review review3= new Review(product3,"muy feas",user3,"no se corresponde con la foto",1);
		
		reviewRepository.save(review1);	
		reviewRepository.save(review2);	
		reviewRepository.save(review3);	
		

//		product1.getReviews().add(review1);
//		product2.getReviews().add(review2);
//		product3.getReviews().add(review3);
//		
//		productRepository.save(product1);
//		productRepository.save(product2);
//		productRepository.save(product3);
		
	}	
}
	