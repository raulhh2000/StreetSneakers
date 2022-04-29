package urjc.dad.controllers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import urjc.dad.models.DataPurchase;
import urjc.dad.models.Email;
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
	@Value("${internalHost}")
	String internalHost;
	
	 @Autowired
	    ShoppingCartRepository shoppingCartRepository;

	    @Autowired
	    UserRepository userRepository;
	    
	    @Autowired
	    PurchaseRepository purchaseRepository;
	    
	    @Autowired
	    ProductRepository productRepository;

	    @GetMapping("/shoppingcart")
	    public String showShoppingCart(Model model, HttpSession sesion,HttpServletRequest request) {
	        Optional<User> user = userRepository.findByEmail(request.getUserPrincipal().getName());
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
	        String feedbackShoppingCart = (String)sesion.getAttribute("feedbackShoppingCart");
			if (feedbackShoppingCart != null) {
				model.addAttribute(feedbackShoppingCart,true);
				sesion.setAttribute("feedbackShoppingCart", null);
			}
	        model.addAttribute("findShoppingCart", findShoppingCart);
	        return "shoppingCart";
	    }
	    
	    @PostMapping("/shoppingcart/buyShoppingCart")
	    public String buyShoppingCart(Model model, HttpSession sesion, HttpServletRequest request) {
	        Optional<User> user = userRepository.findByEmail(request.getUserPrincipal().getName());
	        ShoppingCart shoppingCart = user.get().getShoppingCart();
	        long idPurchase=-1;
	        if(user.get().getPhone()==null) {
	        	sesion.setAttribute("feedbackUser", "mustUpdate");
	        	return "redirect:/user";
	        }
            double totalPrice=0;
            for(Product product : shoppingCart.getListProducts()) {
                totalPrice+=product.getPrice();
            }
            List<LineItem> listLineItems= new ArrayList<>();
            for (Product product: shoppingCart.getListProducts()) {
            	listLineItems.add(new LineItem(product.getName(),product.getDescription(),product.getPrice(),product.getSize(),product.getBrand(),product.getImage()));
            }
            shoppingCart.getListProducts().clear();
            shoppingCartRepository.save(shoppingCart);
            Purchase purchase= new Purchase(user.get(),LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),totalPrice,listLineItems);
            purchaseRepository.save(purchase);
            idPurchase=purchase.getId();
            RestTemplate restTemplate = new RestTemplate();
            DataPurchase dataPurchase = new DataPurchase(purchase.getUser().getName(),
					purchase.getUser().getLastName(),
					purchase.getUser().getEmail(),
					purchase.getUser().getPhone(), 
					purchase.getUser().getAddress(),
					purchase.getUser().getBankAccount(),
					purchase.getDate(),
					purchase.getTotalPrice(),
					purchase.getNumProducts(),
					purchase.getLineItems());
			restTemplate.postForEntity("http://"+internalHost+":8081/email/sendPDF",
					new Email(user.get().getEmail(),
							"Datos del pedido " + purchase.getDate(),
							"Hola " + user.get().getName() + " gracias por realizar una compra en StreetSneakers!!!!\n"
									+ "En este correo te adjuntamos su factura de la compra.\n\n"
									+ "Gracias por confiar en nosotros.\n\n"
									+ "Equipo StreetSneakers.",
							dataPurchase),
					String.class);
	        return "redirect:/purchase/"+idPurchase;
	    }
	    
	    @GetMapping("/shoppingcart/remove/{idProduct}")
	    public String removeProductInShoppingCart(@PathVariable long idProduct,  Model model, HttpSession sesion, HttpServletRequest request) {
	        Optional<User> user =  userRepository.findByEmail(request.getUserPrincipal().getName());
	        ShoppingCart shoppingCart = user.get().getShoppingCart();
	        Optional<Product> product = productRepository.findById(idProduct);
	        if (product.isPresent()) {
	            shoppingCart.getListProducts().remove(product.get());
	            shoppingCartRepository.save(shoppingCart);
	            sesion.setAttribute("feedbackShoppingCart", "removeProductSuccess");
	        }
	        return "redirect:/shoppingcart";
	    }
	
}
