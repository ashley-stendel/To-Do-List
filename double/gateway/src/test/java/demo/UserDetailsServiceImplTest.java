package demo;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import demo.domain.Role;
import demo.domain.User;
import demo.repository.RoleRepository;
import demo.repository.UserRepository;
import demo.services.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
public class UserDetailsServiceImplTest {

	@TestConfiguration
	static class UserDetailsServiceImplTestConfiguration 
	{
		//To test Service class, we need an instance of Service class (as an @Bean) so we can autowire it into our test
		@Bean 
		public UserDetailsServiceImpl userDetailsService()
		{
			return new UserDetailsServiceImpl();
		}
	}
	
	@Autowired 
	private UserDetailsServiceImpl userDetailsService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Before
	public void setUp()
	{
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_TEST"));
		
		User user1 = new User("Shly", "123123123", "123123123");
		user1.setRoles(new HashSet<Role>(roles));
		
		//when making DB calls of things that should be in the db
		//Making sure to return roles
		when(roleRepository.findAll()).thenReturn(roles);
		
		//Making sure to return user1
		when(userRepository.findByUsername(user1.getUsername()))
			.thenReturn(user1);
		
	}
	
	@Test
	public void checkRoleDB()
	{
		
		List<Role> found = roleRepository.findAll();
		assertThat(found.get(0).getName()).isEqualTo("ROLE_TEST");		
	}
	
	@Test 
	public void checkSaveExistingUser()
	{
		//save uses the "findByUsername 
		User user1 = new User("Shly", "123123123", "123123123");
		int result = userDetailsService.save(user1);
		
		assertThat(result).isEqualTo(1); 
	}
	
	@Test
	public void checkSaveNewUser()
	{
		User user1 = new User("Nink", "123123123", "123123123");
		int result = userDetailsService.save(user1);
		
		assertThat(result).isEqualTo(0); 
	}
	
	@Test
	public void checkLoadUser()
	{
		
		User user1 = new User("Shly", "123123123", "123123123");
		
		User found = userRepository.findByUsername(user1.getUsername());		
		assertThat(found.getUsername()).isEqualTo("Shly");
	}
	
	@Test
	public void checkLoadByUsername()
	{
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_TEST"));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User("Shly", "123123123", grantedAuthorities);
	
		UserDetails found = userDetailsService.loadUserByUsername("Shly");
		
		assertThat(found).isEqualTo(userDetails);
	
	}
	
	
}
