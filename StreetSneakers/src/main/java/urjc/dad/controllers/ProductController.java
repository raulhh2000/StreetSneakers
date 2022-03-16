package urjc.dad.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.Product;
import urjc.dad.models.Review;
import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.ReviewRepository;
import urjc.dad.repositories.ShoppingCartRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@GetMapping("/product/{idProduct}")
	public String showProduct(@PathVariable long idProduct,  Model model,HttpSession sesion,HttpServletRequest request) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		if(findProduct) {
			Principal currentEmail= request.getUserPrincipal();
			if(currentEmail != null && request.isUserInRole("USER")) {
				User user = userRepository.findByEmail(currentEmail.getName()).get();	
				model.addAttribute("favorite",user.getWishList().contains(product.get()));
			
				ShoppingCart shoppingCart= user.getShoppingCart();
				model.addAttribute("shoppigncart",shoppingCart.getListProducts().contains(product.get()));
			}
			model.addAttribute("product", product.get());
			List<Review> reviews=product.get().getReviews();
			boolean findReviews = !reviews.isEmpty();
			if(findReviews) {
				List<Review> ownReviews =new ArrayList<>();
				List<Review> otherReviews =new ArrayList<>();
				List<Review> adminReviews =new ArrayList<>();
				if (currentEmail == null) {
					otherReviews.addAll(reviews);
				} else if (request.isUserInRole("ADMIN")) {
					adminReviews.addAll(reviews);
				} else {
					User user = userRepository.findByEmail(currentEmail.getName()).get();
					for (Review review: reviews) {
						if (user.equals(review.getUser())) {
							ownReviews.add(review);
						} else {
							otherReviews.add(review);
						}
					}
				}
				model.addAttribute("ownReviews", ownReviews);
				model.addAttribute("otherReviews", otherReviews);
				model.addAttribute("adminReviews", adminReviews);
			}
			model.addAttribute("findReviews", findReviews);
			model.addAttribute("numReviews", reviews.size());
		}
		String feedbackProduct = (String)sesion.getAttribute("feedbackProduct");
		if (feedbackProduct != null) {
			model.addAttribute(feedbackProduct,true);
			sesion.setAttribute("feedbackProduct", null);
		}
		model.addAttribute("findProduct", findProduct);
	    return "product";
	}
	
	@GetMapping("product/{idProduct}/addFavorite")
	public String addFavorite(@PathVariable long idProduct, Model model, HttpServletRequest request) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
		if(findProduct) {
			user.getWishList().add(product.get());
			userRepository.save(user);
		}
		return "redirect:/product/{idProduct}";
	}
	@GetMapping("product/{idProduct}/removeFavorite")
	public String removeFavorite(@PathVariable long idProduct, Model model,HttpServletRequest request) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
		if(findProduct) {
			user.getWishList().remove(product.get());
			userRepository.save(user);
		}
		return "redirect:/product/{idProduct}";
	}
	
	@GetMapping("product/{idProduct}/addShoppingCart")
    public String addShoppingCart(@PathVariable long idProduct, Model model, HttpServletRequest request) {
        Optional<Product> product=productRepository.findById(idProduct);
    	User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
        ShoppingCart shoppingCart= user.getShoppingCart();
        shoppingCart.getListProducts().add(product.get());
        shoppingCartRepository.save(shoppingCart);
        return "redirect:/product/{idProduct}";
    }
	@GetMapping("product/{idProduct}/removeShoppingCart")
    public String removeShoppingCart(@PathVariable long idProduct, Model model, HttpServletRequest request) {
        Optional<Product> product=productRepository.findById(idProduct);
    	User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
        ShoppingCart shoppingCart= user.getShoppingCart();
        shoppingCart.getListProducts().remove(product.get());
        shoppingCartRepository.save(shoppingCart);
        return "redirect:/product/{idProduct}";
    }
	
	@PostMapping("product/{idProduct}/review")
	public String insertReview(@PathVariable long idProduct, Review review, Model model,HttpSession sesion, HttpServletRequest request) {
		User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
		review.setUser(user);
		review.setProduct(productRepository.getById(idProduct));
		sesion.setAttribute("feedbackProduct","reviewComplete" );
		reviewRepository.save(review);
	    return "redirect:/product/{idProduct}";
	}
	
	@GetMapping("product/{idProduct}/review/{idReview}")
	public String modifyReview(@PathVariable long idProduct, @PathVariable long idReview, Model model,HttpSession sesion, HttpServletRequest request) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		if(findProduct) {
			Principal currentEmail = request.getUserPrincipal();
			if (currentEmail != null && request.isUserInRole("USER")) {
				User user = userRepository.findByEmail(currentEmail.getName()).get();
				model.addAttribute("favorite",user.getWishList().contains(product.get()));
				
				ShoppingCart shoppingCart= user.getShoppingCart();
				model.addAttribute("shoppigncart",shoppingCart.getListProducts().contains(product.get()));
			}
			model.addAttribute("product", product.get());
			List<Review> reviews=product.get().getReviews();
			boolean findReviews = !reviews.isEmpty();
			if(findReviews) {
				List<Review> ownReviews =new ArrayList<>();
				List<Review> otherReviews =new ArrayList<>();
				User user = userRepository.findByEmail(currentEmail.getName()).get();
				for (Review review: reviews) {
					if (user.equals(review.getUser())) {
						if (review.getId() != idReview) {
							ownReviews.add(review);
						}
					} else {
						otherReviews.add(review);
					}
				}
				model.addAttribute("ownReviews", ownReviews);
				model.addAttribute("otherReviews", otherReviews);
			}
			model.addAttribute("findReviews", findReviews);
			model.addAttribute("numReviews", reviews.size());
			Optional<Review> currentReview =reviewRepository.findById(idReview);
			if(currentReview.isPresent()) {
				model.addAttribute("review", currentReview.get());
				model.addAttribute("modifyReview", true);
			}
			else {
				sesion.setAttribute("feedbackProduct","modifyNoComplete" );
			}
		}
		String feedbackProduct = (String)sesion.getAttribute("feedbackProduct");
		if (feedbackProduct != null) {
			model.addAttribute(feedbackProduct,true);
			sesion.setAttribute("feedbackProduct", null);
		}
		model.addAttribute("findProduct", findProduct);
	    return "product";
	}
	
	@PostMapping("product/{idProduct}/review/{idReview}")
	public String modifyReview(@PathVariable long idProduct, @PathVariable long idReview, Review review, Model model,HttpSession sesion, HttpServletRequest request) {
		User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
		review.setUser(user);
		review.setProduct(productRepository.getById(idProduct));
		review.setId(idReview);
		sesion.setAttribute("feedbackProduct","modifyComplete" );
		reviewRepository.save(review);
	    return "redirect:/product/{idProduct}";
	}
	
	@GetMapping("product/{idProduct}/review/remove/{idReview}")
	public String removeReview(@PathVariable long idProduct, @PathVariable long idReview, Model model,HttpSession sesion) {
		Optional<Review> review = reviewRepository.findById(idReview);
		if (review.isPresent()) {
			reviewRepository.deleteById(idReview);
			sesion.setAttribute("feedbackProduct","removeComplete" );
		}else {
			sesion.setAttribute("feedbackProduct","removeNoComplete" );
		}
	    return "redirect:/product/{idProduct}";
	}
	
}
