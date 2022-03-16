package urjc.dad.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/loginerror").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		http.authorizeRequests().antMatchers("/filterName").permitAll();
		http.authorizeRequests().antMatchers("/filterSize").permitAll();
		http.authorizeRequests().antMatchers("/filterPrice").permitAll();
		http.authorizeRequests().antMatchers("/filterBrand").permitAll();
		http.authorizeRequests().antMatchers("/product/*").permitAll();

		// Private pages (all other pages)
		http.authorizeRequests().antMatchers("/user/**").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/product/*/review/remove/**").hasAnyRole("USER","ADMIN");
		http.authorizeRequests().antMatchers("/product/**").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/purchase/**").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/shoppingcart/**").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN");

		// Login form
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("email");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/loginerror");

		// Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");

		// Disable CSRF at the moment
		//http.csrf().disable();
	}

}
