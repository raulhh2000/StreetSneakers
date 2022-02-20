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

import urjc.dad.models.Admin;
import urjc.dad.models.Product;
import urjc.dad.models.Purchase;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AdminRepository adminRepository;
	
	@GetMapping("/user/{idUser}")
	public String showUser(@PathVariable long idUser, Model model, HttpSession sesion) {
		User user = userRepository.findById(idUser).get();
		model.addAttribute("user", user);
		model.addAttribute("find", user.getPhone() != null);
		List<Purchase> list=user.getPurchases();
		boolean findPurchases= !list.isEmpty();
		if(findPurchases) {
			model.addAttribute("purchases", list);
		}
		model.addAttribute("findPurchases", findPurchases);
		
		List<Product> wishList=user.getWishList();
		boolean findWishList= !wishList.isEmpty();
		if(findWishList) {
			model.addAttribute("wishList", wishList);
		}
		model.addAttribute("findWishList", findWishList);
		String feedback = (String)sesion.getAttribute("feedback");
		if (feedback != null) {
			model.addAttribute(feedback,true);
			sesion.setAttribute("feedback", null);
		}
	    return "user";
	}
	
	@PostMapping("/user/update/{idUser}")
	public String modifyUser(@PathVariable long idUser, User user, Model model, HttpSession sesion) {
		Optional<User> isUser=userRepository.findByEmail(user.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(user.getEmail());
		User oldUser = userRepository.findById(idUser).get();
		if(user.getEmail().equals(oldUser.getEmail()) || (!isUser.isPresent() && !isAdmin.isPresent())) {
			user.setId(idUser);
			user.setPurchases(oldUser.getPurchases());
			user.setWishList(oldUser.getWishList());
			userRepository.save(user);
			sesion.setAttribute("feedback", "updatedUserSuccess");
		} else {
			sesion.setAttribute("feedback", "updatedUserFailure");
		}
	    return "redirect:/user/{idUser}";
	}
}
