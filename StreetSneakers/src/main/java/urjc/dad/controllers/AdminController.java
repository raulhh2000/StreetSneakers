package urjc.dad.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.Admin;
import urjc.dad.models.Product;
import urjc.dad.models.Review;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;
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
	
	@GetMapping("/admin/{idAdmin}")
	public String showAdmin(@PathVariable long idAdmin, Model model) {
		Admin admin = adminRepository.findById(idAdmin).get();
		model.addAttribute("id", admin.getId());
		model.addAttribute("name", admin.getName());
		model.addAttribute("lastName", admin.getLastName());
		model.addAttribute("email", admin.getEmail());
		boolean find = admin.getPassword() != null;
		if (find) {
			model.addAttribute("password", admin.getPassword());
		}
		model.addAttribute("find", find);
		model.addAttribute("products",productRepository.findAll());
	    return "admin";
	}

	@PostMapping("/admin/update/{idAdmin}")
	public String modifyAdmin(@PathVariable long idAdmin, Admin admin, Model model) {
		admin.setId(idAdmin);
		adminRepository.save(admin);
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/addProduct")
	public String addProduct(@PathVariable long idAdmin, Product product, Model model) {
		productRepository.save(product);
		return "redirect:/admin/{idAdmin}";
	}
	
	@PostMapping("/admin/{idAdmin}/removeProduct")
	public String removeProduct(@PathVariable long idAdmin,long productId, Model model) {
		productRepository.deleteById(productId);
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
		return "redirect:/admin/{idAdmin}";
	}
	
}
