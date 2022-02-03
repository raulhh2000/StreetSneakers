package urjc.dad.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.dad.models.Product;
import urjc.dad.repositories.ProductRepository;

@Controller
public class HomeController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/")
	public String showHome(Model model) {
		List<Product> list=productRepository.findAll();
		
		model.addAttribute("products", list);
		model.addAttribute("find", true);
	    return "home";
	}
	
	
	@GetMapping("/filterName")
	public String showHomeByName(@RequestParam(required=false) String nameFilter, Model model) {
		Optional<Product> product =productRepository.findByName(nameFilter);
		boolean find=product.isPresent();
		if(find) {
			model.addAttribute("products", product);
		}
		model.addAttribute("find", find);
		return "home";
	}
	
	
	@GetMapping("/filterSize")
	public String showHomeBySize(@RequestParam(required=false) double size, Model model) {
		List<Product> listProduct =productRepository.findBySize(size);
		boolean find=!listProduct.isEmpty();
		if(find) {
			model.addAttribute("products", listProduct);
		}
		model.addAttribute("find", find);
		return "home";
	}
		
	 
	
}
