package demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

//Need to define this method so gateway knows the roles of the user 
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		 /**
		  * requires auth for every url of application 
		  * generate login form
		  * allow user to lockout 
		  * Prevent CSRF attack 
		  * Session fixation protection
		  * Integrates with: httpservletrequest.getremoteuser, .getuserprincipal, .isUserInRole, .login, .logout
		  */
	/*
		auth.inMemoryAuthentication()
		.withUser("user").password("password").roles("USER") //these defines users with specific roles
		.and()
		.withUser("admin").password("admin").roles("USER", "ADMIN", "READER", "WRITER")
		.and()
		.withUser("audit").password("audit").roles("USER", "ADMIN", "READER");
		// @formatter:on
	}*/
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


	//access decision go to ADMIN application 
	//use spring security 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.httpBasic().and()
		.logout().and()
		.authorizeRequests()
			.antMatchers("/index.html", "/login", "/register","/alltasks", "/").permitAll()		
			.anyRequest().authenticated()
			.and()
		.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		// @formatter:on
	}
}