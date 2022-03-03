package urjc.dad.security;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import urjc.dad.models.Admin;
import urjc.dad.models.User;
import urjc.dad.repositories.AdminRepository;
import urjc.dad.repositories.UserRepository;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String userEmail;
		String userPassword;
		List<String> userRoles;
		Optional<User> user = userRepository.findByEmail(auth.getName());
		if (!user.isPresent()) {
			Optional<Admin> admin = adminRepository.findByEmail(auth.getName());
			if (!admin.isPresent()) {
				throw new BadCredentialsException("User not found");
			} else {
				userEmail = admin.get().getEmail();
				userPassword = admin.get().getPassword();
				userRoles= admin.get().getRoles();
			}
		} else {
			userEmail = user.get().getEmail();
			userPassword = user.get().getPassword();
			userRoles= user.get().getRoles();
		}
		String password = (String) auth.getCredentials();
		if (!passwordEncoder().matches(password, userPassword)) {
			throw new BadCredentialsException("Wrong password");
		}

		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role));
		}
		return new UsernamePasswordAuthenticationToken(userEmail, password, roles);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}