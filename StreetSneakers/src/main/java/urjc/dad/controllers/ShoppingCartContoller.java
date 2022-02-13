package urjc.dad.controllers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import urjc.dad.models.Product;
import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;
import urjc.dad.repositories.ShoppingCartRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class ShoppingCartContoller {

	 @Autowired
	    ShoppingCartRepository shoppingCartRepository;

	    @Autowired
	    UserRepository userRepository;

	    @GetMapping("/shoppingcart/{idUser}")
	    public String showShoppingCart(@PathVariable long idUser,  Model model) {
	        Optional<User> user = userRepository.findById(idUser);
	        Optional<ShoppingCart> shoppingCart=shoppingCartRepository.findByUser(user.get());
	        boolean findShoppingCart=shoppingCart.isPresent();
	        if(findShoppingCart) {
	            model.addAttribute("shoppingCart", shoppingCart.get());
	            model.addAttribute("products", shoppingCart.get().getListProducts());
	            double totalPrice=0;
	            for(Product product : shoppingCart.get().getListProducts()) {
	                totalPrice+=product.getPrice();
	            }
	            model.addAttribute("totalPrice", totalPrice);
	        }
	        model.addAttribute("findShoppingCart", findShoppingCart);
	        return "shoppingCart";
	    }
	
}
