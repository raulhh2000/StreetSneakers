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
			model.addAttribute("products", product.get());
		}
		model.addAttribute("find", find);
		return "home";
	}
		
	@GetMapping("/filterPrice")
	public String showHomeByPrice(Double priceMin, Double priceMax, Model model) {
		List<Product> products;
		if (priceMin != null && priceMax != null) {
			products =productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(priceMin, priceMax);
		} else if (priceMin != null) {
			products =productRepository.findByPriceGreaterThanEqual(priceMin);
		} else if (priceMax != null){
			products =productRepository.findByPriceLessThanEqual(priceMax);
		} else {
			products =productRepository.findAll();
		}
		boolean find=!products.isEmpty();
		if(find) {
			model.addAttribute("products", products);
		}
		model.addAttribute("find", find);
		return "home";
	}
	
}
