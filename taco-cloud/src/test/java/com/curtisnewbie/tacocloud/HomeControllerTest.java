package com.curtisnewbie.tacocloud;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @SpringBootTest // this tests the MVC, using @SpringBootTest is not enough
@WebMvcTest(HomeController.class) // test the controller
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")) // mock a "/" request
				.andExpect(MockMvcResultMatchers.status().isOk()) // the status is 200 OK
				.andExpect(MockMvcResultMatchers.view().name("home")) // template is "home.html"
				.andExpect(MockMvcResultMatchers.content() // response body contains this string
						.string(Matchers.containsString("Welcome to...")));
	}

}
