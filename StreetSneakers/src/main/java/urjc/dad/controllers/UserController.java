package urjc.dad.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/user")
	public String showUser(Model model, HttpSession sesion, HttpServletRequest request) {
		User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
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
		String feedbackUser = (String)sesion.getAttribute("feedbackUser");
		if (feedbackUser != null) {
			model.addAttribute(feedbackUser,true);
			sesion.setAttribute("feedbackUser", null);
		}
	    return "user";
	}
	
	@PostMapping("/user/update")
	public String modifyUser(User user, Model model, HttpSession sesion, HttpServletRequest request){
		Optional<User> isUser=userRepository.findByEmail(user.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(user.getEmail());
		User oldUser = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
		if(user.getEmail().equals(oldUser.getEmail()) || (!isUser.isPresent() && !isAdmin.isPresent())) {
			user.setId(oldUser.getId());
			user.setPurchases(oldUser.getPurchases());
			user.setWishList(oldUser.getWishList());
			user.setShoppingCart(oldUser.getShoppingCart());
			if (!oldUser.getPassword().equals(user.getPassword())) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			user.setRoles(oldUser.getRoles());
			userRepository.save(user);
			sesion.setAttribute("feedbackUser", "updatedUserSuccess");
		} else {
			sesion.setAttribute("feedbackUser", "updatedUserFailure");
		}
	    return "redirect:/user";
	}
	
	@GetMapping("/login")
	 public String login() {
		return "login";
	 }

	 @GetMapping("/loginerror")
	 public String loginerror() {
		 return "loginerror";
	 }
}
