package demo.controllers;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.User;
import demo.repository.UserRepository;
import demo.services.UserDetailsServiceImpl;

@RestController
public class GatewayController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	@RequestMapping("/user")
	@ResponseBody
	public Map<String, Object> user(Principal user) {
		System.out.println(user.toString());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", user.getName());
		map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user)
				.getAuthorities()));
		return map;
	}

	@RequestMapping("/login")
	public String login() {
		return "forward:/";
	}
	

	@RequestMapping("/register")
	public List<String> register(@Valid@RequestBody User user, BindingResult bindingResult) {
		
		//checking if any User Validation has not been respected
		if(!bindingResult.hasErrors())
		{
			//ensuring unique user
			if (userDetailsServiceImpl.save(user) == 1)
					bindingResult.rejectValue("username", "userError", null, "Username already exists, please try again");	
		}
	
		
		//returning list of errors
		List<String> errors = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
																	 .collect(Collectors.<String> toList());
		return errors;
	}
	

}
