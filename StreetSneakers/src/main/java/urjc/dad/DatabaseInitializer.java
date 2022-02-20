package urjc.dad;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import urjc.dad.models.Admin;
import urjc.dad.models.LineItem;
import urjc.dad.models.Product;
import urjc.dad.models.Review;
import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;
import urjc.dad.repositories.ShoppingCartRepository;
import urjc.dad.repositories.UserRepository;

@Component
public class DatabaseInitializer {

	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
    private ShoppingCartRepository shoppingCartRepository;


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
		
		Product product1 = new Product("AirForce1", "Zapatilla grande", 123.4, 44, "Nike", "/images/AirForce1.png");
		Product product2 = new Product("Jordan Verde", "Zapatilla de baloncesto", 99.99, 42, "Nike", "/images/JordanVerde.png");
		Product product3 = new Product("Ultraboost", "Zapatilla comoda", 180, 41, "Adidas", "/images/Ultraboost.png");
		Product product4 = new Product("Yeezy Oreo", "Zapatilla bonita", 300.25, 40, "Adidas", "/images/YeezyOreo.png");
		Product product5 = new Product("Regreso al futuro", "Zapatilla futurista", 29000.99, 42, "Nike", "/images/Regresoalfuturo.png");
		Product product6 = new Product("Triple S", "Zapatilla de lujo", 550, 41, "Balenciaga", "/images/TripleS.png");
		Product product7 = new Product("VaporMax", "Zapatilla con suela de aire", 180.4, 39, "Nike", "/images/VaporMax.png");
		Product product8 = new Product("Yeezy 700 Wave", "Zapatilla de moda", 250.5, 42, "Adidas", "/images/Yeezy700Wave.png");
		Product product9 = new Product("Alexander Mcqueen", "Zapatillas de lujo", 525, 43, "Alexander Mcqueen", "/images/AlexanderMcqueen.png");
		Product product10 = new Product("Stan Smith", "Zapatilla basica", 79.39, 40, "Nike", "/images/StanSmith.png");
		Product product11 = new Product("Jordan X Off-White Roja", "Zapatilla de colaboracion", 3000.2, 42, "Nike", "/images/JordanXOff-WhiteRoja.png");
		Product product12 = new Product("AirForce1 X Off-White", "Zapatillas de colaboracion", 1500, 41, "Nike", "/images/AirForce1XOff-White.png");
		
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		productRepository.save(product4);
		productRepository.save(product5);
		productRepository.save(product6);
		productRepository.save(product7);
		productRepository.save(product8);
		productRepository.save(product9);
		productRepository.save(product10);
		productRepository.save(product11);
		productRepository.save(product12);
		
		user1.getWishList().add(product1);
		userRepository.save(user1);
		
		
		List<LineItem> list= new ArrayList<>();
		list.add(new LineItem(product1.getName(),product1.getDescription(),product1.getPrice(),product1.getSize(),product1.getBrand(),product1.getImage(),2));
		list.add(new LineItem(product2.getName(),product2.getDescription(),product2.getPrice(),product2.getSize(),product2.getBrand(),product2.getImage(),2));
		list.add(new LineItem(product3.getName(),product3.getDescription(),product3.getPrice(),product3.getSize(),product3.getBrand(),product3.getImage(),1));

		//LocalDateTime date =  LocalDateTime.of(2022,02,02,10,00,00);
		//Purchase purchase1= new Purchase(user1,date,300.02,list);
		//purchaseRepository.save(purchase1);
		
		//user1.getPurchases().add(purchase1);
		//userRepository.save(user1);
		
		Review review1= new Review(product1,"muy bonitas",user1,"perfectas",5);
		Review review2= new Review(product2,"muy grandes",user2,"mala talla",2);
		Review review3= new Review(product3,"muy feas",user3,"no se corresponde con la foto",1);
		
		reviewRepository.save(review1);	
		reviewRepository.save(review2);	
		reviewRepository.save(review3);	
		
		List<Product> listProducts= new ArrayList<>();
		listProducts.add(product1);
		listProducts.add(product2);
		listProducts.add(product3);
		
		ShoppingCart shoppingCart= new ShoppingCart(user1);
        shoppingCart.setListProducts(listProducts);

        shoppingCartRepository.save(shoppingCart);

//		product1.getReviews().add(review1);
//		product2.getReviews().add(review2);
//		product3.getReviews().add(review3);
//		
//		productRepository.save(product1);
//		productRepository.save(product2);
//		productRepository.save(product3);
		
	}	
}
	