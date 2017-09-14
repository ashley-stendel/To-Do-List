package demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.User;
import demo.services.UserDetailsServiceImpl;

@RestController
public class RegisterController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@PostMapping("/register")
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
