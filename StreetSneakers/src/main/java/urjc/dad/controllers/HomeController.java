package urjc.dad.controllers;

import java.util.List;

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
		List<Product> listProduct=productRepository.findAll();
		boolean find=!listProduct.isEmpty();
		if(find) {
			model.addAttribute("products", listProduct);
			model.addAttribute("find", find);
		} else {
			model.addAttribute("notFind", true);
		}
		model.addAttribute("brands", productRepository.findDistinctBrand());
	    return "home";
	}
	
	
	@GetMapping("/filterName")
	public String showHomeByName(@RequestParam(required=false) String nameFilter, Model model) {
		List<Product> listProduct =productRepository.findByNameContainsIgnoreCase(nameFilter);
		boolean find=!listProduct.isEmpty();
		if(find) {
			model.addAttribute("products", listProduct);
			model.addAttribute("find", find);
		} else {
			model.addAttribute("notFindFilterName", true);
		}
		
		model.addAttribute("brands", productRepository.findDistinctBrand());
		model.addAttribute("filterName", true);
		model.addAttribute("nameFilter", nameFilter);
		model.addAttribute("filtering", true);
		return "home";
	}
	
	
	@GetMapping("/filterSize")
	public String showHomeBySize(@RequestParam(required=false) double size, Model model) {
		List<Product> listProduct =productRepository.findBySize(size);
		boolean find=!listProduct.isEmpty();
		if(find) {
			model.addAttribute("products", listProduct);
			model.addAttribute("find", find);
		} else {
			model.addAttribute("notFindFilterSize", true);
		}
		model.addAttribute("brands", productRepository.findDistinctBrand());
		model.addAttribute("filterSize", true);
		model.addAttribute("sizeFilter", size);
		model.addAttribute("filtering", true);
		return "home";
	}
		
	@GetMapping("/filterPrice")
	public String showHomeByPrice(Double priceMin, Double priceMax, Model model) {
		List<Product> products;
		if (priceMin != null && priceMax != null) {
			products =productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(priceMin, priceMax);
			model.addAttribute("filterPriceMin", true);
			model.addAttribute("priceMinFilter", priceMin);
			model.addAttribute("filterPriceMax", true);
			model.addAttribute("priceMaxFilter", priceMax);
		} else if (priceMin != null) {
			products =productRepository.findByPriceGreaterThanEqual(priceMin);
			model.addAttribute("filterPriceMin", true);
			model.addAttribute("priceMinFilter", priceMin);
		} else if (priceMax != null){
			products =productRepository.findByPriceLessThanEqual(priceMax);
			model.addAttribute("filterPriceMax", true);
			model.addAttribute("priceMaxFilter", priceMax);
		} else {
			products =productRepository.findAll();
		}
		boolean find=!products.isEmpty();
		if(find) {
			model.addAttribute("products", products);
		}
		model.addAttribute("find", find);
		model.addAttribute("brands", productRepository.findDistinctBrand());
		model.addAttribute("filtering", true);
		return "home";
	}
	
	@GetMapping("/filterBrand")
	public String showHomeByBrand(@RequestParam(required=false) String brand, Model model) {
		List<Product> products = productRepository.findByBrand(brand);
		boolean find=!products.isEmpty();
		if(find) {
			model.addAttribute("products", products);
			model.addAttribute("find", find);
		} else {
			model.addAttribute("notFindFilterBrand", true);
		}
		model.addAttribute("brands", productRepository.findDistinctBrand());
		model.addAttribute("filterBrand", true);
		model.addAttribute("brandFilter", brand);
		model.addAttribute("filtering", true);
		return "home";
	}
	
}
