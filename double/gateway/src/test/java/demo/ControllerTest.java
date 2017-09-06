package demo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.domain.User;
import demo.services.UserDetailsServiceImpl;

//This project does not make much sense to test the connection to controller
//As there is no specific unique return from controller
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	//Spring Boot Test creates the bean so we can autowire it
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	@Test
	public void loginMappingReturn() throws Exception 
	{
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
													 .andExpect(content().string(containsString("forward:/")));
	}
	
	//checking if register endpoint is forbidden - no session 
	@Test
	public void registerEndpoint() throws Exception 
	{
		
		User user = new User("Bob", "123123123", "123123123");
		
		mockMvc.perform(post("/register")
				.content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
				//.andDo(print());
				//.accept(MediaType.APPLICATION_JSON));
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
}
