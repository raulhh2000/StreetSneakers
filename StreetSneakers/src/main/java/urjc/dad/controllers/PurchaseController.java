package urjc.dad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import urjc.dad.models.Purchase;
import urjc.dad.repositories.PurchaseRepository;


@Controller
public class PurchaseController {

	@Autowired
	PurchaseRepository purchaseRepository;
	
	@GetMapping("/purchase/{idPurchase}")
	public String showPurchase(@PathVariable long idPurchase, Model model) {
		Purchase purchase = purchaseRepository.findById(idPurchase).get();
		model.addAttribute("purchase", purchase);
		model.addAttribute("products", purchase.getLineItems());
		return "purchase";
	}
}
