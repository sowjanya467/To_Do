package com.todo.note.userservicetest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.todo.note.ToDoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ToDoApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void RegistrationTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"emailId\": \"msowjanya2014@gmail.com\", \"password\" : \"Sowji@123\", \"phoneNumber\" : \"9100289102\" , \"userNmae\": \"sowjanya\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				//.andExpect(jsonPath("$.message").value("Registeration Successfull!!"))
				//.andExpect(jsonPath("$.status").value(200)).andDo(print())
		        .andExpect(jsonPath("$.message").value("Something went wrong"))
                .andExpect(jsonPath("$.status").value(-1)).andDo(print());
	}

	/*@Test
	public void activationTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/activateaccount").requestAttr("request", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1YjU1YzVlODRkNTczMDEzZWU1NTJhOTQiLCJpYXQiOjE1MzI0Mjg3NDgsImlzcyI6Im1zb3dqYW55YTIwMTRAZ21haWwuY29tIn0.oP0BUn6IR0y0U8Q3k9MdOffX658UMUTXMQ-JBpRCQPA")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("account activated Successfull!!"));

	}*/

	@Test
	public void forgotPasswordTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/forgotpassword").param("emailId", "msowjanya2014@gmail.com")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("link to set your password has been sent successfully"))
				.andExpect(jsonPath("$.status").value(200)).andDo(print());
	}
	
	/*@Test
	public void setpasswordTest() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/resetpassword/").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"newPassword\": \"Sowji@765\", \"confirmPassword\" : \"Sowji@765\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("password changed successfully!!!"))
                .andExpect(jsonPath("$.status").value(200)).andDo(print());
	}*/
	
}
