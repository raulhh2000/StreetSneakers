package urjc.dad.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import urjc.dad.models.Admin;
import urjc.dad.models.Email;
import urjc.dad.models.Product;
import urjc.dad.models.Review;
import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;
import urjc.dad.repositories.ShoppingCartRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class AdminController {
	
	@Autowired
	AdminRepository adminRepository;
	

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/admin")
	public String showAdmin(@RequestParam(defaultValue = "0") long productId, Model model, HttpSession sesion, HttpServletRequest request) {
		Admin admin = adminRepository.findByEmail(request.getUserPrincipal().getName()).get();
		model.addAttribute("admin", admin);
		model.addAttribute("products",productRepository.findAll());
		String feedbackAdmin = (String)sesion.getAttribute("feedbackAdmin");
		if (feedbackAdmin != null) {
			model.addAttribute(feedbackAdmin,true);
			sesion.setAttribute("feedbackAdmin", null);
		}
		if (productId != 0) {
			model.addAttribute("modifyProduct", true);
			model.addAttribute("product", productRepository.findById(productId).get());
		}
	    return "admin";
	}
	
	@PostMapping("/admin/update")
	public String modifyAdmin(Admin admin, Model model, HttpSession sesion, HttpServletRequest request) {
		Optional<User> isUser=userRepository.findByEmail(admin.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(admin.getEmail());
		Admin oldAdmin = adminRepository.findByEmail(request.getUserPrincipal().getName()).get();
		if(admin.getEmail().equals(oldAdmin.getEmail()) || (!isUser.isPresent() && !isAdmin.isPresent())) {
			admin.setId(oldAdmin.getId());
			if (!oldAdmin.getPassword().equals(admin.getPassword())) {
				admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			}
			admin.setRoles(oldAdmin.getRoles());
			adminRepository.save(admin);
			sesion.setAttribute("feedbackAdmin", "updatedAdminSuccess");
		} else {
			sesion.setAttribute("feedbackAdmin", "updatedAdminFailure");
		}
		return "redirect:/admin";
	}
	
	@PostMapping("/admin/addProduct")
	public String addProduct(@RequestParam MultipartFile file, Product product, Model model, HttpSession sesion, HttpServletRequest request) throws IOException {
		Optional<Product> isProduct = productRepository.findByNameIgnoreCase(product.getName());
		if (!isProduct.isPresent()) {
			if (file.getOriginalFilename().contains(product.getName().replace(" ", ""))) {
				Path imagePath = Paths.get("src/main/resources/images/sneakers").resolve(file.getOriginalFilename());
				file.transferTo(imagePath);
				product.setImage("/images/sneakers/"+file.getOriginalFilename());
				productRepository.save(product);
				sesion.setAttribute("feedbackAdmin", "addedProductSuccess");
			} else {
				sesion.setAttribute("feedbackAdmin", "addedProductFailure");
			}
		} else {
			sesion.setAttribute("feedbackAdmin", "addedProductFailure");
		}
		return "redirect:/admin";
	}
	
	@PostMapping("/admin/removeProduct")
	public String removeProduct(long productId, Model model, HttpSession sesion, HttpServletRequest request) {
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			List<User> users= userRepository.findAll();
			for (User user: users) {
				user.getWishList().remove(product.get());
				userRepository.save(user);
			}
			
			List<ShoppingCart> shoppingCarts= shoppingCartRepository.findAll();
			for (ShoppingCart shoppingCart: shoppingCarts) {
				shoppingCart.getListProducts().remove(product.get());
				shoppingCartRepository.save(shoppingCart);
			}
			sesion.setAttribute("feedbackAdmin", "removedProductSuccess");
			productRepository.deleteById(productId);
		} else {
			sesion.setAttribute("feedbackAdmin", "removedProductFailure");
		}
		return "redirect:/admin";
	}
	
	@PostMapping("/admin/modifyProduct/{idProduct}")
	public String modifyProduct(@PathVariable long idProduct, @RequestParam MultipartFile file,
			Product product, Model model, HttpSession sesion, HttpServletRequest request) throws IOException {
		Optional<Product> isProduct = productRepository.findByNameIgnoreCase(product.getName());
		Product oldProduct = productRepository.findById(idProduct).get();
		if (product.getName().equals(oldProduct.getName()) || !isProduct.isPresent()) {	
			product.setReviews(oldProduct.getReviews());
			product.setId(idProduct);
			if (!file.isEmpty()) {
				if (file.getOriginalFilename().contains(product.getName().replace(" ", ""))) {
					Path imagePath = Paths.get("src/main/resources/images/sneakers").resolve(file.getOriginalFilename());
					file.transferTo(imagePath);
					product.setImage("/images/sneakers/"+file.getOriginalFilename());
					productRepository.save(product);
					sesion.setAttribute("feedbackAdmin", "modifiedProductSuccess");
				} else {
					sesion.setAttribute("feedbackAdmin", "modifiedProductFailure");
				}
			} else {
				if (!product.getName().equals(oldProduct.getName())) {
					String oldImagePath=oldProduct.getImage();
                    String oldExtension = oldProduct.getImage().substring(oldProduct.getImage().length()-4);
                    File oldImage = new File("src/main/resources"+oldImagePath);
                    String imagePath="src/main/resources/images/sneakers/"+product.getName().replace(" ", "")+oldExtension;
                    Files.copy(Paths.get(oldImage.getPath()), Paths.get(imagePath));
                    product.setImage("/images/sneakers/"+product.getName().replace(" ", "")+oldExtension);
				} else {
					product.setImage(oldProduct.getImage());
				}
				productRepository.save(product);
				sesion.setAttribute("feedbackAdmin", "modifiedProductSuccess");
			}		
		} else {
			sesion.setAttribute("feedbackAdmin", "modifiedProductFailure");
		}
		return "redirect:/admin";
	}

	@PostMapping("/admin/createAdmin")
	public String createAdmin(Admin admin, Model model, HttpSession sesion, HttpServletRequest request) {
		Optional<User> isUser=userRepository.findByEmail(admin.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(admin.getEmail());
		if(!isUser.isPresent() && !isAdmin.isPresent()) {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			admin.setRoles(Arrays.asList("ROLE_ADMIN"));
			adminRepository.save(admin);
			sesion.setAttribute("feedbackAdmin", "addedAdminSuccess");
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity("http://localhost:8080/email/send",
					new Email(admin.getEmail(),
							"Mensaje de bienvenida",
							"Bienvenido " + admin.getName() + " a la familia StreetSneakers!!!!\n"
									+ "Tiene una cuenta de tipo ADMINISTRADOR.\n\n"
									+ "Equipo StreetSneakers."),
					String.class);
		} else {
			sesion.setAttribute("feedbackAdmin", "addedAdminFailure");
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/removeUser")
	public String removeUser(String email, Model model, HttpSession sesion, HttpServletRequest request) {
		Optional<User> user=userRepository.findByEmail(email);
		if(user.isPresent()) {
			List<Review> list= reviewRepository.findByUser(user.get());
			reviewRepository.deleteAll(list);
			userRepository.delete(user.get());
			sesion.setAttribute("feedbackAdmin", "removedAdminSuccess");
		}
		else{
			Optional<Admin> admin=adminRepository.findByEmail(email);
			Admin currentAdmin = adminRepository.findByEmail(request.getUserPrincipal().getName()).get();
			if(admin.isPresent() && admin.get().getId()!=currentAdmin.getId()) {
				adminRepository.delete(admin.get());
				sesion.setAttribute("feedbackAdmin", "removedAdminSuccess");
			} else {
				sesion.setAttribute("feedbackAdmin", "removedAdminFailure");
			}
		}
		
		return "redirect:/admin";
	}
}
