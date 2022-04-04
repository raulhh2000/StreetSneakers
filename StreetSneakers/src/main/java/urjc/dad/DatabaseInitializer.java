package urjc.dad;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		
		Admin admin1= new Admin("Raul", "Heredia", "raulHeredia@gmail.com", passwordEncoder.encode("1234"), "ROLE_ADMIN");
		Admin admin2= new Admin("Alberto", "Martin", "albertoMartin@gmail.com", passwordEncoder.encode("4567"), "ROLE_ADMIN");
		Admin admin3= new Admin("Fatima", "Smounat ", "fatimaSmounat@gmail.com", passwordEncoder.encode("8901"), "ROLE_ADMIN");
		adminRepository.save(admin1);
		adminRepository.save(admin2);
		adminRepository.save(admin3);
		
		User user1= new User("Juanma", "Rodriguez", "juanmaRodriguez@gmail.com", passwordEncoder.encode("1234"), "ROLE_USER");
		User user2= new User("Benja","Fernandez", "benjaFernandez@gmail.com", passwordEncoder.encode("4567"), "ROLE_USER");
		User user3= new User("Adri","Espada","adriEspada@gmail.com", passwordEncoder.encode("8901"), "ROLE_USER");
		
		ShoppingCart shoppingCartUser1= new ShoppingCart(user1);
		user1.setShoppingCart(shoppingCartUser1);
		user2.setShoppingCart(new ShoppingCart(user2));
		user3.setShoppingCart(new ShoppingCart(user3));
		
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/HDQWNpL/Air-Force1.png"), new File("temp.png"));
		File tempFile = new File("temp.png");
		Product product1 = new Product("AirForce1", "Zapatilla grande", 123.4, 44, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/z47C1sb/Jordan-Verde.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product2 = new Product("Jordan Verde", "Zapatilla de baloncesto", 99.99, 42, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/SXrKjH6/Ultraboost.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product3 = new Product("Ultraboost", "Zapatilla comoda", 180, 41, "Adidas", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/y42rf48/Yeezy-Oreo.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product4 = new Product("Yeezy Oreo", "Zapatilla bonita", 300.25, 40, "Adidas", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/t2Sxvd4/Regresoalfuturo.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product5 = new Product("Regreso al futuro", "Zapatilla futurista", 29000.99, 42, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/WsfGnQV/TripleS.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product6 = new Product("Triple S", "Zapatilla de lujo", 550, 41, "Balenciaga", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/Jxt4YQJ/VaporMax.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product7 = new Product("VaporMax", "Zapatilla con suela de aire", 180.4, 39, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/CVGwM68/Yeezy700-Wave.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product8 = new Product("Yeezy 700 Wave", "Zapatilla de moda", 250.5, 42, "Adidas", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/2PY6nZt/Alexander-Mcqueen.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product9 = new Product("Alexander Mcqueen", "Zapatillas de lujo", 525, 43, "Alexander Mcqueen", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/3RFHB9N/Stan-Smith.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product10 = new Product("Stan Smith", "Zapatilla basica", 79.39, 40, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/xjL837s/Jordan-XOff-White-Roja.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product11 = new Product("Jordan X Off-White Roja", "Zapatilla de colaboracion", 3000.2, 42, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		FileUtils.copyURLToFile(new URL("https://i.ibb.co/T090sr0/Air-Force1-XOff-White.png"), new File("temp.png"));
		tempFile = new File("temp.png");
		Product product12 = new Product("AirForce1 X Off-White", "Zapatillas de colaboracion", 1500, 41, "Nike", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)));
		tempFile.delete();
		
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
	