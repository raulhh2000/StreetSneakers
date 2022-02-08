package urjc.dad.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.dad.models.Admin;
import urjc.dad.models.Product;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;

@Controller
public class AdminController {
	
	@Autowired
	AdminRepository adminRepository;
	

	@Autowired
	ProductRepository productRepository;
	

	@Autowired
	ReviewRepository reviewRepository;
	
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
}
