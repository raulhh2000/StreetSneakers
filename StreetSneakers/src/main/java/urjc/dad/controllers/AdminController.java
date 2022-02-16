package urjc.dad.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping("/admin/{idAdmin}")
	public String showAdmin(@PathVariable long idAdmin, @RequestParam(defaultValue = "0") long productId, Model model) {
		Admin admin = adminRepository.findById(idAdmin).get();
		model.addAttribute("admin", admin);
		boolean find = admin.getPassword() != null;
		model.addAttribute("find", find);
		model.addAttribute("products",productRepository.findAll());
		if (productId != 0) {
			model.addAttribute("modifyProduct", true);
			model.addAttribute("product", productRepository.findById(productId).get());
		}
	    return "admin";
	}

	@PostMapping("/admin/update/{idAdmin}")
	public String modifyAdmin(@PathVariable long idAdmin, Admin admin, Model model) {
		admin.setId(idAdmin);
		adminRepository.save(admin);
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/addProduct")
	public String addProduct(@PathVariable long idAdmin, @RequestParam MultipartFile file, Product product, Model model) throws IOException {
		Path imagePath = Paths.get("src//main//resources//static//images").resolve(file.getOriginalFilename());
		file.transferTo(imagePath);
		product.setImage("images//"+file.getOriginalFilename());
		productRepository.save(product);
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/removeProduct")
	public String removeProduct(@PathVariable long idAdmin,long productId, Model model) {
		Product product = productRepository.findById(productId).get();
		List<User> users= userRepository.findAll();
		for (User user: users) {
			user.getWishList().remove(product);
			userRepository.save(user);
		}
		
		List<ShoppingCart> shoppingCarts= shoppingCartRepository.findAll();
		for (ShoppingCart shoppingCart: shoppingCarts) {
			shoppingCart.getListProducts().remove(product);
			shoppingCartRepository.save(shoppingCart);
		}
		
		productRepository.deleteById(productId);
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/modifyProduct/{idProduct}")
	public String modifyProduct(@PathVariable long idAdmin, @PathVariable long idProduct, @RequestParam MultipartFile file, Product product, Model model) throws IOException {
		Product oldProduct = productRepository.findById(idProduct).get();
		product.setReviews(oldProduct.getReviews());
		product.setId(idProduct);
		if (!file.isEmpty()) {
			Path imagePath = Paths.get("src//main//resources//static//images").resolve(file.getOriginalFilename());
			file.transferTo(imagePath);
			product.setImage("images//"+file.getOriginalFilename());
		} else {
			product.setImage(oldProduct.getImage());
		}
		productRepository.save(product);
		return "redirect:/admin/{idAdmin}";
	}

	@PostMapping("/admin/{idAdmin}/createAdmin")
	public String createAdmin(@PathVariable long idAdmin,Admin admin, Model model) {
		adminRepository.save(admin);
		return "redirect:/admin/{idAdmin}";
	}
	
	@GetMapping("/admin/{idAdmin}/removeUser")
	public String removeUser(@PathVariable long idAdmin,String email, Model model) {
		Optional<User> user=userRepository.findByEmail(email);
		if(user.isPresent()) {
			List<Review> list= reviewRepository.findByUser(user.get());
			reviewRepository.deleteAll(list);
			userRepository.delete(user.get());
		}
		else{
			Optional<Admin> admin=adminRepository.findByEmail(email);
			if(admin.isPresent() && admin.get().getId()!=idAdmin) {
				adminRepository.delete(admin.get());
			}
		}
		
		return "redirect:/admin/{idAdmin}";
	}
}
