package urjc.dad.controllers;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import urjc.dad.models.Admin;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.UserRepository;

@Controller
public class AuthenticationController {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	 public String login(Model model, HttpSession sesion) {
		String feedbackUser = (String)sesion.getAttribute("feedbackUser");
		if (feedbackUser != null) {
			model.addAttribute(feedbackUser,true);
			sesion.setAttribute("feedbackUser", null);
		}
		return "login";
	 }

	 @GetMapping("/loginerror")
	 public String loginerror() {
		 return "loginerror";
	 }
	 
	@PostMapping("/signIn")
	public String createUser(User user, Model model, HttpSession sesion) {
		Optional<User> isUser=userRepository.findByEmail(user.getEmail());
		Optional<Admin> isAdmin=adminRepository.findByEmail(user.getEmail());
		if(!isUser.isPresent() && !isAdmin.isPresent()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("ROLE_USER"));
			userRepository.save(user);
			sesion.setAttribute("feedbackUser", "addedUserSuccess");
		} else {
			sesion.setAttribute("feedbackUser", "addedUserFailure");
		}
		return "redirect:/login";
	}
}