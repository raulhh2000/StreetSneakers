package urjc.dad.controllers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.Product;
import urjc.dad.models.Purchase;
import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;
import urjc.dad.repositories.ProductRepository;
import urjc.dad.repositories.PurchaseRepository;
import urjc.dad.repositories.ShoppingCartRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class ShoppingCartContoller {

	 @Autowired
	    ShoppingCartRepository shoppingCartRepository;

	    @Autowired
	    UserRepository userRepository;
	    
	    @Autowired
	    PurchaseRepository purchaseRepository;
	    
	    @Autowired
	    ProductRepository productRepository;

	    @GetMapping("/shoppingcart/{idUser}")
	    public String showShoppingCart(@PathVariable long idUser,  Model model) {
	        Optional<User> user = userRepository.findById(idUser);
	        Optional<ShoppingCart> shoppingCart=shoppingCartRepository.findByUser(user.get());
	        boolean findShoppingCart=shoppingCart.isPresent();
	        if(findShoppingCart) {
	            model.addAttribute("shoppingCart", shoppingCart.get());
	            model.addAttribute("products", shoppingCart.get().getListProducts());
	            model.addAttribute("user", user.get());
	            double totalPrice=0;
	            for(Product product : shoppingCart.get().getListProducts()) {
	                totalPrice+=product.getPrice();
	            }
	            model.addAttribute("totalPrice", totalPrice);
	        }
	        model.addAttribute("findShoppingCart", findShoppingCart);
	        return "shoppingCart";
	    }
	    
	    @PostMapping("/shoppingcart/{idUser}/buyShoppingCart")
	    public String buyShoppingCart(@PathVariable long idUser,  Model model) {
	        Optional<User> user = userRepository.findById(idUser);
	        Optional<ShoppingCart> shoppingCart=shoppingCartRepository.findByUser(user.get());
	        boolean findShoppingCart=shoppingCart.isPresent();
	        if(findShoppingCart) {
	            double totalPrice=0;
	            for(Product product : shoppingCart.get().getListProducts()) {
	                totalPrice+=product.getPrice();
	            }
	            List<Product> listProducts= new ArrayList<>();
	            listProducts.addAll(shoppingCart.get().getListProducts());
	            Purchase purchase= new Purchase(user.get(),LocalDateTime.now(),totalPrice,listProducts);
	            purchaseRepository.save(purchase);
	            shoppingCartRepository.delete(shoppingCart.get());
	        }
	        return "redirect:/shoppingcart/{idUser}";
	    }
	    
	    @GetMapping("/shoppingcart/{idUser}remove/{idProduct}")
	    public String removeProductInShoppingCart(@PathVariable long idUser,  Model model, @PathVariable long idProduct) {
	        Optional<User> user = userRepository.findById(idUser);
	        Optional<ShoppingCart> shoppingCart=shoppingCartRepository.findByUser(user.get());
	        boolean findShoppingCart=shoppingCart.isPresent();
	        if(findShoppingCart) {
	            Product product = productRepository.getById(idProduct);
	            shoppingCart.get().getListProducts().remove(product);
	            shoppingCartRepository.save(shoppingCart.get());
	        }
	        return "redirect:/shoppingcart/{idUser}";
	    }
	
}
