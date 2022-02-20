package urjc.dad.controllers;

import java.util.List;
import java.util.Optional;

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
	public String showProduct(@PathVariable long idProduct,  Model model,HttpSession sesion) {
		Optional<Product> product=productRepository.findById(idProduct);
		model.addAttribute("favorite",userRepository.findById((long)4).get().getWishList().contains(product.get()));
		boolean findProduct=product.isPresent();
		Optional<ShoppingCart> shoppingCart= shoppingCartRepository.findByUser(userRepository.getById((long)4));
        if(shoppingCart.isPresent())
            model.addAttribute("shoppigncart",shoppingCart.get().getListProducts().contains(product.get()));
        else
            model.addAttribute("shoppigncart",false);
		if(findProduct) {
			model.addAttribute("product", product.get());
			List<Review> reviews=product.get().getReviews();
			boolean findReviews = !reviews.isEmpty();
			if(findReviews) {
				model.addAttribute("reviews", reviews);
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
	@GetMapping("product/{idProduct}/removeFavorite")
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
	
	@GetMapping("product/{idProduct}/addShoppingCart")
    public String addShoppingCart(@PathVariable long idProduct, Model model) {
        Optional<Product> product=productRepository.findById(idProduct);
        Optional<ShoppingCart> shoppingCart= shoppingCartRepository.findByUser(userRepository.getById((long)4));
        if(shoppingCart.isPresent()) {
            shoppingCart.get().getListProducts().add(product.get());
            shoppingCartRepository.save(shoppingCart.get());
        }
        else {
            ShoppingCart shoppingCartUser = new ShoppingCart(userRepository.getById((long)4));
            shoppingCartUser.getListProducts().add(product.get());
            shoppingCartRepository.save(shoppingCartUser);
        }
        return "redirect:/product/{idProduct}";
    }
	@GetMapping("product/{idProduct}/removeShoppingCart")
    public String removeShoppingCart(@PathVariable long idProduct, Model model) {
        Optional<Product> product=productRepository.findById(idProduct);
        Optional<ShoppingCart> shoppingCart= shoppingCartRepository.findByUser(userRepository.getById((long)4));
        if(shoppingCart.isPresent()) {
            shoppingCart.get().getListProducts().remove(product.get());
            shoppingCartRepository.save(shoppingCart.get());
        }
        return "redirect:/product/{idProduct}";
    }
	
	@PostMapping("product/{idProduct}/review")
	public String insertReview(@PathVariable long idProduct, Review review, Model model,HttpSession sesion) {
		review.setUser(userRepository.findById((long)4).get());
		review.setProduct(productRepository.getById(idProduct));
		sesion.setAttribute("feedbackProduct","reviewComplete" );
		reviewRepository.save(review);
	    return "redirect:/product/{idProduct}";
	}
	
	@GetMapping("product/{idProduct}/review/{idReview}")
	public String modifyReview(@PathVariable long idProduct, @PathVariable long idReview, Model model,HttpSession sesion) {
		Optional<Product> product=productRepository.findById(idProduct);
		boolean findProduct=product.isPresent();
		if(findProduct) {
			model.addAttribute("favorite",userRepository.findById((long)4).get().getWishList().contains(product.get()));
			model.addAttribute("product", product.get());
			List<Review> reviews=product.get().getReviews();
			boolean findReviews = !reviews.isEmpty();
			if(findReviews) {
				model.addAttribute("reviews", reviews);
			}
			model.addAttribute("findReviews", findReviews);
			model.addAttribute("numReviews", reviews.size());
			Optional<Review> review =reviewRepository.findById(idReview);
			if(review.isPresent()) {
				model.addAttribute("review", review.get());
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
	public String modifyReview(@PathVariable long idProduct, @PathVariable long idReview, Review review, Model model,HttpSession sesion) {
		review.setUser(userRepository.findById((long)4).get());
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
