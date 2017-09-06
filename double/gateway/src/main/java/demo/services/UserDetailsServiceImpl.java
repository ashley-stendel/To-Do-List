package demo.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.domain.Role;
import demo.domain.User;
import demo.repository.RoleRepository;
import demo.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	//here we obvi need our repository 
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//required method
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		//convert to a UserDetails
		//UserDetails is an interface 
		//User class implements UserDetails and constructor takes in username, password, and all GrantedAuthorities 
		//GrantedAuthority is an authority given to an Authentication Object
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		for (Role role : user.getRoles())
			grantedAuthorities.add(new SimpleGrantedAuthority (role.getName()));


		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}


	public boolean exists(User user)
	{
		if (userRepository.findByUsername(user.getUsername()) != null)
			return true;
		
		return false;
	}
	

	public int save(User user) 
	{	
		boolean exists = exists(user);
		
		if (exists)
			return 1;
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user); 
		return 0;
	}

	public List<User> findAll()
	{
		return userRepository.findAll();
	}
}
