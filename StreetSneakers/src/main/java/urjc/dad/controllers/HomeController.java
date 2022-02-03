package urjc.dad.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import urjc.dad.models.Product;
import urjc.dad.repositories.ProductRepository;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("")
	public String showHome(Model model) {
		List<Product> list=productRepository.findAll();
		model.addAttribute("products", list);
	    return "home";
	}
	 
	
}
