package urjc.dad.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.Product;
import urjc.dad.models.Review;
import urjc.dad.models.User;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/product/{idProduct}")
	public String showProduct(@PathVariable long idProduct,  Model model) {
		Optional<Product> product=productRepository.findById(idProduct);
		model.addAttribute("favorite",userRepository.findById((long)4).get().getWishList().contains(product.get()));
		boolean findProduct=product.isPresent();
		if(findProduct) {
			model.addAttribute("product", product.get());
			List<Review> reviews=product.get().getReviews();
			boolean findReviews = !reviews.isEmpty();
			if(findReviews) {
				model.addAttribute("reviews", reviews);
			}
			model.addAttribute("findReviews", findReviews);	
		}
		model.addAttribute("findProduct", findProduct);
	    return "product";
	}
	
	@PostMapping("product/{idProduct}/addFavorite")
	public String addFavorite(@PathVariable long idProduct, Model model) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		User user=userRepository.findById((long)4).get();
		if(findProduct) {
			user.getWishList().add(product.get());
			userRepository.save(user);
		}
		return "redirect:/product/{idProduct}";
	}
	@PostMapping("product/{idProduct}/removeFavorite")
	public String removeFavorite(@PathVariable long idProduct, Model model) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		User user=userRepository.findById((long)4).get();
		if(findProduct) {
			user.getWishList().remove(product.get());
			userRepository.save(user);
		}
		return "redirect:/product/{idProduct}";
	}
	
	
	@PostMapping("product/{idProduct}/review")
	public String insertReview(@PathVariable long idProduct, Review review, Model model) {
		review.setUser(userRepository.findById((long)4).get());
		review.setProduct(productRepository.getById(idProduct));
		reviewRepository.save(review);
	    return "redirect:/product/{idProduct}";
	}
	
	@GetMapping("product/{idProduct}/review/{idReview}")
	public String modifyReview(@PathVariable long idProduct, @PathVariable long idReview, Model model) {
		Optional<Product> product=productRepository.findById(idProduct);
		model.addAttribute("favorite",userRepository.findById((long)4).get().getWishList().contains(product.get()));
		boolean findProduct=product.isPresent();
		if(findProduct) {
			model.addAttribute("product", product.get());
			List<Review> reviews=product.get().getReviews();
			boolean findReviews = !reviews.isEmpty();
			if(findReviews) {
				model.addAttribute("reviews", reviews);
			}
			model.addAttribute("findReviews", findReviews);
			model.addAttribute("review", reviewRepository.findById(idReview).get());
			model.addAttribute("modifyReview", true);
		}
		model.addAttribute("findProduct", findProduct);
	    return "product";
	}
	
	@PostMapping("product/{idProduct}/review/{idReview}")
	public String modifyReview(@PathVariable long idProduct, @PathVariable long idReview, Review review, Model model) {
		review.setUser(userRepository.findById((long)4).get());
		review.setProduct(productRepository.getById(idProduct));
		review.setId(idReview);
		reviewRepository.save(review);
	    return "redirect:/product/{idProduct}";
	}
	
	@GetMapping("product/{idProduct}/review/remove/{idReview}")
	public String removeReview(@PathVariable long idProduct, @PathVariable long idReview, Model model) {
		Optional<Review> review = reviewRepository.findById(idReview);
		if (review.isPresent()) {
			reviewRepository.deleteById(idReview);
		}
	    return "redirect:/product/{idProduct}";
	}
	
}
