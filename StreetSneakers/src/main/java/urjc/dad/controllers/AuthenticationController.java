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
import org.springframework.web.client.RestTemplate;

import urjc.dad.models.Admin;
import urjc.dad.models.Email;
import urjc.dad.models.ShoppingCart;
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
			user.setShoppingCart(new ShoppingCart(user));
			userRepository.save(user);
			sesion.setAttribute("feedbackUser", "addedUserSuccess");
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity("http://localhost:8080/email/send",
					new Email(user.getEmail(),
							"Mensaje de bienvenida",
							"Bienvenido " + user.getName() + " a la familia StreetSneakers!!!!\n\n"
									+ "Equipo StreetSneakers."),
					String.class);
		} else {
			sesion.setAttribute("feedbackUser", "addedUserFailure");
		}
		return "redirect:/login";
	}
}