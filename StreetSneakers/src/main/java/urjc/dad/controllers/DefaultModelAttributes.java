package urjc.dad.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import urjc.dad.models.Admin;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.UserRepository;

@ControllerAdvice
public class DefaultModelAttributes {

	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@ModelAttribute("adminRole")
	public boolean adminRole(HttpServletRequest request) {
		return  request.isUserInRole("ADMIN");
	}
	
	@ModelAttribute("userRole")
	public boolean userRole(HttpServletRequest request) {
		return request.isUserInRole("USER");
	}
	
	@ModelAttribute("register")
	public boolean register(HttpServletRequest request) {
		return request.isUserInRole("ADMIN") || request.isUserInRole("USER");
	}
	
	@ModelAttribute("idAdmin")
	public String idAdmin(HttpServletRequest request) {
		if(request.isUserInRole("ADMIN")) {
			Admin admin = adminRepository.findByEmail(request.getUserPrincipal().getName()).get();
			return String.valueOf(admin.getId());
		}
		else {
			return String.valueOf(-1);
		}
	}
	@ModelAttribute("idUser")
	public String idUser(HttpServletRequest request) {
		if(request.isUserInRole("USER")) {
			User user = userRepository.findByEmail(request.getUserPrincipal().getName()).get();
			return String.valueOf(user.getId());
		}
		else {
			return String.valueOf(-1);
		}
	}

}
