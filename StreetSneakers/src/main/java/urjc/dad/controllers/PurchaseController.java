package urjc.dad.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import urjc.dad.models.Product;
import urjc.dad.models.Purchase;
import urjc.dad.models.Review;
import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;
import urjc.dad.repositories.PurchaseRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class PurchaseController {

	@Autowired
	PurchaseRepository purchaseRepository;
	
	@GetMapping("/purchase/{idPurchase}")
	public String showPurchase(@PathVariable long idPurchase, Model model) {
		Purchase purchase = purchaseRepository.findById(idPurchase).get();
		model.addAttribute("user", purchase.getUser());
		model.addAttribute("products", purchase.getProducts());
		model.addAttribute("priceTotal", purchase.getTotalPrice());
		model.addAttribute("date", purchase.getDate());
		return "purchase";
	}
}
