package urjc.dad.controllers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.LineItem;
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
	        ShoppingCart shoppingCart = user.get().getShoppingCart();
	        List<Product> listProducts = shoppingCart.getListProducts();
	        boolean findShoppingCart = !listProducts.isEmpty();
	        if (findShoppingCart) {
	        	model.addAttribute("products", listProducts);
		        model.addAttribute("user", user.get());
		        double totalPrice=0;
		        for(Product product : listProducts) {
		        	totalPrice+=product.getPrice();
		        }
		        model.addAttribute("totalPrice", totalPrice);
	        }
	        model.addAttribute("findShoppingCart", findShoppingCart);
	        return "shoppingCart";
	    }
	    
	    @PostMapping("/shoppingcart/{idUser}/buyShoppingCart")
	    public String buyShoppingCart(@PathVariable long idUser,  Model model, HttpSession sesion) {
	        Optional<User> user = userRepository.findById(idUser);
	        ShoppingCart shoppingCart = user.get().getShoppingCart();
	        long idPurchase=-1;
	        if(user.get().getPhone()==null) {
	        	sesion.setAttribute("feedbackUser", "mustUpdate");
	        	return "redirect:/user/"+idUser;
	        }
            double totalPrice=0;
            for(Product product : shoppingCart.getListProducts()) {
                totalPrice+=product.getPrice();
            }
            List<LineItem> listLineItems= new ArrayList<>();
            for (Product product: shoppingCart.getListProducts()) {
            	listLineItems.add(new LineItem(product.getName(),product.getDescription(),product.getPrice(),product.getSize(),product.getBrand(),product.getImage(),1));
            }
            shoppingCart.getListProducts().clear();
            shoppingCartRepository.save(shoppingCart);
            Purchase purchase= new Purchase(user.get(),LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),totalPrice,listLineItems);
            purchaseRepository.save(purchase);
            idPurchase=purchase.getId();
	        return "redirect:/purchase/"+idPurchase;
	    }
	    
	    @GetMapping("/shoppingcart/{idUser}remove/{idProduct}")
	    public String removeProductInShoppingCart(@PathVariable long idUser, @PathVariable long idProduct,  Model model, HttpSession sesion) {
	        Optional<User> user = userRepository.findById(idUser);
	        ShoppingCart shoppingCart = user.get().getShoppingCart();
	        Optional<Product> product = productRepository.findById(idProduct);
	        if (product.isPresent()) {
	            shoppingCart.getListProducts().remove(product.get());
	            shoppingCartRepository.save(shoppingCart);
	        }
	        return "redirect:/shoppingcart/{idUser}";
	    }
	
}
