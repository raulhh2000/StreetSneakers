package urjc.dad;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import urjc.dad.models.Admin;
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
		Admin admin2= new Admin("Alberto", "Martin", "albertoMartin@gmail.com", "4567");
		Admin admin3= new Admin("Fatima", "Smounat ", "fatimaSmounat@gmail.com", "8901");
		adminRepository.save(admin1);
		adminRepository.save(admin2);
		adminRepository.save(admin3);
		
		User user1= new User("Juanma", "Rodriguez", "juanmaRodriguez@gmail.com", "1234");
		User user2= new User("Benja","Fernandez", "benjaFernandez@gmail.com", "4567");
		User user3= new User("Adri","Espada","adriEspada@gmail.com", "8901");
		
		ShoppingCart shoppingCartUser1= new ShoppingCart(user1);
		user1.setShoppingCart(shoppingCartUser1);
		user2.setShoppingCart(new ShoppingCart(user2));
		user3.setShoppingCart(new ShoppingCart(user3));
		
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		
		Product product1 = new Product("AirForce1", "Zapatilla grande", 123.4, 44, "Nike", "/images/sneakers/AirForce1.png");
		Product product2 = new Product("Jordan Verde", "Zapatilla de baloncesto", 99.99, 42, "Nike", "/images/sneakers/JordanVerde.png");
		Product product3 = new Product("Ultraboost", "Zapatilla comoda", 180, 41, "Adidas", "/images/sneakers/Ultraboost.png");
		Product product4 = new Product("Yeezy Oreo", "Zapatilla bonita", 300.25, 40, "Adidas", "/images/sneakers/YeezyOreo.png");
		Product product5 = new Product("Regreso al futuro", "Zapatilla futurista", 29000.99, 42, "Nike", "/images/sneakers/Regresoalfuturo.png");
		Product product6 = new Product("Triple S", "Zapatilla de lujo", 550, 41, "Balenciaga", "/images/sneakers/TripleS.png");
		Product product7 = new Product("VaporMax", "Zapatilla con suela de aire", 180.4, 39, "Nike", "/images/sneakers/VaporMax.png");
		Product product8 = new Product("Yeezy 700 Wave", "Zapatilla de moda", 250.5, 42, "Adidas", "/images/sneakers/Yeezy700Wave.png");
		Product product9 = new Product("Alexander Mcqueen", "Zapatillas de lujo", 525, 43, "Alexander Mcqueen", "/images/sneakers/AlexanderMcqueen.png");
		Product product10 = new Product("Stan Smith", "Zapatilla basica", 79.39, 40, "Nike", "/images/sneakers/StanSmith.png");
		Product product11 = new Product("Jordan X Off-White Roja", "Zapatilla de colaboracion", 3000.2, 42, "Nike", "/images/sneakers/JordanXOff-WhiteRoja.png");
		Product product12 = new Product("AirForce1 X Off-White", "Zapatillas de colaboracion", 1500, 41, "Nike", "/images/sneakers/AirForce1XOff-White.png");
		
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
		user1.getWishList().add(product3);
		user1.getWishList().add(product5);
		user1.getWishList().add(product7);
		user1.getWishList().add(product11);
		userRepository.save(user1);
		
		List<Product> listProducts= new ArrayList<>();
		listProducts.add(product1);
		listProducts.add(product2);
		listProducts.add(product3);
		shoppingCartUser1.setListProducts(listProducts);
        shoppingCartRepository.save(shoppingCartUser1);
		
        Review review1= new Review(product1,"Muy bonitas",user1,"Perfectas",5);
		Review review2= new Review(product2,"Muy grandes",user2,"Talla Mal",2);
		Review review3= new Review(product3,"Muy feas",user3,"No se corresponde con la foto",1);
		Review review4= new Review(product1,"Yo pensaba que Nike era buena marca pero me he comprado estas zapatillas hace un mes y están destrozadas ya",user2,"Decepción enorme",0);
		Review review5= new Review(product1,"Para el precio que tienen no estan mal",user3,"Buena relación calidad/precio",3);
		Review review6= new Review(product2,"Yo pensaba que Nike era buena marca pero me he comprado estas zapatillas hace un mes y están destrozadas ya",user1,"Decepción enorme",0);
		Review review7= new Review(product2,"Para el precio que tienen no estan mal",user3,"Buena relación calidad/precio",3);
		Review review8= new Review(product3,"Yo pensaba que Adidas era buena marca pero me he comprado estas zapatillas hace un mes y están destrozadas ya",user2,"Decepción enorme",0);
		Review review9= new Review(product3,"Para el precio que tienen no estan mal",user1,"Buena relación calidad/precio",3);
		
		reviewRepository.save(review1);	
		reviewRepository.save(review2);	
		reviewRepository.save(review3);
		reviewRepository.save(review4);	
		reviewRepository.save(review5);	
		reviewRepository.save(review6);
		reviewRepository.save(review7);	
		reviewRepository.save(review8);	
		reviewRepository.save(review9);
		
	}	
}
	