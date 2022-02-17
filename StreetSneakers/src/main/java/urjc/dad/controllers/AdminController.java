package urjc.dad.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
	public String showAdmin(@PathVariable long idAdmin, @RequestParam(defaultValue = "0") long productId, Model model, HttpSession sesion) {
		Admin admin = adminRepository.findById(idAdmin).get();
		model.addAttribute("admin", admin);
		boolean find = admin.getPassword() != null;
		model.addAttribute("find", find);
		model.addAttribute("products",productRepository.findAll());
		String feedback = (String)sesion.getAttribute("feedback");
		if (feedback != null) {
			model.addAttribute(feedback,true);
			sesion.setAttribute("feedback", null);
		}
		if (productId != 0) {
			model.addAttribute("modifyProduct", true);
			model.addAttribute("product", productRepository.findById(productId).get());
		}
	    return "admin";
	}

	@PostMapping("/admin/update/{idAdmin}")
	public String modifyAdmin(@PathVariable long idAdmin, Admin admin, Model model, HttpSession sesion) {
		Optional<User> isUser=userRepository.findByEmail(admin.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(admin.getEmail());
		Admin oldAdmin = adminRepository.findById(idAdmin).get();
		if(admin.getEmail().equals(oldAdmin.getEmail()) || (!isUser.isPresent() && !isAdmin.isPresent())) {
			admin.setId(idAdmin);
			adminRepository.save(admin);
			sesion.setAttribute("feedback", "updatedAdminSuccess");
		} else {
			sesion.setAttribute("feedback", "updatedAdminFailure");
		}
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/addProduct")
	public String addProduct(@PathVariable long idAdmin, @RequestParam MultipartFile file, Product product, Model model, HttpSession sesion) throws IOException {
		Optional<Product> isProduct = productRepository.findByName(product.getName());
		if (!isProduct.isPresent()) {
			if (file.getOriginalFilename().contains(product.getName())) {
				Path imagePath = Paths.get("src//main//resources//static//images").resolve(file.getOriginalFilename());
				file.transferTo(imagePath);
				product.setImage("images//"+file.getOriginalFilename());
				productRepository.save(product);
				sesion.setAttribute("feedback", "addedProductSuccess");
			} else {
				sesion.setAttribute("feedback", "addedProductFailure");
			}
		} else {
			sesion.setAttribute("feedback", "addedProductFailure");
		}
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/removeProduct")
	public String removeProduct(@PathVariable long idAdmin,long productId, Model model, HttpSession sesion) {
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
			sesion.setAttribute("feedback", "removedProductSuccess");
			productRepository.deleteById(productId);
		} else {
			sesion.setAttribute("feedback", "removedProductFailure");
		}
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/modifyProduct/{idProduct}")
	public String modifyProduct(@PathVariable long idAdmin, @PathVariable long idProduct, @RequestParam MultipartFile file,
			Product product, Model model, HttpSession sesion) throws IOException {
		Optional<Product> isProduct = productRepository.findByName(product.getName());
		Product oldProduct = productRepository.findById(idProduct).get();
		if (product.getName().equals(oldProduct.getName()) || !isProduct.isPresent()) {	
			product.setReviews(oldProduct.getReviews());
			product.setId(idProduct);
			if (!file.isEmpty()) {
				if (file.getOriginalFilename().contains(product.getName())) {
					Path imagePath = Paths.get("src//main//resources//static//images").resolve(file.getOriginalFilename());
					file.transferTo(imagePath);
					product.setImage("images//"+file.getOriginalFilename());
					productRepository.save(product);
					sesion.setAttribute("feedback", "modifiedProductSuccess");
				} else {
					sesion.setAttribute("feedback", "modifiedProductFailure");
				}
			} else {
				if (!product.getName().equals(oldProduct.getName())) {
					String oldImagePath="src//main//resources//static//"+oldProduct.getImage();
					String oldExtension = oldProduct.getImage().substring(oldProduct.getImage().length()-4);
					File oldImage = new File(oldImagePath);
					String imagePath="src//main//resources//static//images//"+product.getName()+oldExtension;
					File image = new File(imagePath);
					oldImage.renameTo(image);
					product.setImage("images//"+product.getName()+oldExtension);
				} else {
					product.setImage(oldProduct.getImage());
				}
				productRepository.save(product);
				sesion.setAttribute("feedback", "modifiedProductSuccess");
			}		
		} else {
			sesion.setAttribute("feedback", "modifiedProductFailure");
		}
		return "redirect:/admin/{idAdmin}";
	}

	@PostMapping("/admin/{idAdmin}/createAdmin")
	public String createAdmin(@PathVariable long idAdmin,Admin admin, Model model, HttpSession sesion) {
		Optional<User> isUser=userRepository.findByEmail(admin.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(admin.getEmail());
		if(!isUser.isPresent() && !isAdmin.isPresent()) {
			adminRepository.save(admin);
			sesion.setAttribute("feedback", "addedAdminSuccess");
		} else {
			sesion.setAttribute("feedback", "addedAdminFailure");
		}
		return "redirect:/admin/{idAdmin}";
	}
	
	@GetMapping("/admin/{idAdmin}/removeUser")
	public String removeUser(@PathVariable long idAdmin,String email, Model model, HttpSession sesion) {
		Optional<User> user=userRepository.findByEmail(email);
		if(user.isPresent()) {
			List<Review> list= reviewRepository.findByUser(user.get());
			reviewRepository.deleteAll(list);
			userRepository.delete(user.get());
			sesion.setAttribute("feedback", "removedAdminSuccess");
		}
		else{
			Optional<Admin> admin=adminRepository.findByEmail(email);
			if(admin.isPresent() && admin.get().getId()!=idAdmin) {
				adminRepository.delete(admin.get());
				sesion.setAttribute("feedback", "removedAdminSuccess");
			} else {
				sesion.setAttribute("feedback", "removedAdminFailure");
			}
		}
		
		return "redirect:/admin/{idAdmin}";
	}
}
