package urjc.dad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.User;
import urjc.dad.repositories.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/user/{idUser}")
	public String showUser(@PathVariable long idUser, Model model) {
		User user = userRepository.findById(idUser).get();
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("email", user.getEmail());
		boolean find = user.getPassword() != null;
		if (find) {
			model.addAttribute("password", user.getPassword());
			model.addAttribute("phone", user.getPhone());
			model.addAttribute("address", user.getAddress());
			model.addAttribute("bankAccount", user.getBankAccount());
		}
		model.addAttribute("find", find);
	    return "user";
	}
	
	@PostMapping("/user/update/{idUser}")
	public String modifyUser(@PathVariable long idUser, User user, Model model) {
		User oldUser = userRepository.findById(idUser).get();
		user.setId(idUser);
		user.setPurchases(oldUser.getPurchases());
		user.setWishList(oldUser.getWishList());
		userRepository.save(user);
	    return "redirect:/user/{idUser}";
	}
}
