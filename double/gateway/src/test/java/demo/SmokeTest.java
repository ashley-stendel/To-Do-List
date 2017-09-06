package demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import demo.controllers.GatewayController;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SmokeTest 
{
	//injected before TestMethod Runs
	@Autowired 
	private GatewayController gatewayController;
	
	@Test
	public void contextLoads() throws Exception
	{
		assertThat(gatewayController).isNotNull();
	}
}
	


