package com.demo.woof.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import com.demo.woof.domain.Sitter;
import com.demo.woof.service.SitterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SitterController.class)
public class SitterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SitterService service;
	
	@Autowired
	ObjectMapper objMapper;
	
	@Test
	public void testGetById() throws Exception {
		
		String name = "Amy B.";
		String email = "amy.b@test.com";
		String phone = "4251231234";
				
		long id = 1;
		Sitter sitter = new Sitter(name, email, phone);
		sitter.setId(id);
		
		given(service.getSitterById(id)).willReturn(sitter);
		
		mockMvc.perform(get("/sitters?id=" + id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id", is(1)))
		.andExpect(jsonPath("name", is(name)))
		.andExpect(jsonPath("email", is(email)))
		.andExpect(jsonPath("phone", is(phone)));
	}
	
	@Test
	public void testCreate() throws Exception {
		
		String name = "Amy B.";
		String email = "amy.b@test.com";
		String phone = "+14251231234";
				
		Sitter sitter = new Sitter(name, email, phone);
		given(service.createSitter(any(Sitter.class))).willReturn(sitter);
		
		mockMvc.perform(post("/sitters").contentType(MediaType.APPLICATION_JSON)
		.characterEncoding("UTF-8").content(objMapper.writeValueAsBytes(sitter)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id", is(instanceOf(Integer.class))))
		.andExpect(jsonPath("name", is(name)))
		.andExpect(jsonPath("email", is(email)))
		.andExpect(jsonPath("phone", is(phone)))
		.andExpect(jsonPath("imageUrl", is(nullValue())))
		.andExpect(jsonPath("score", is((nullValue()))))
		.andExpect(jsonPath("avgRating", is(0)))
		.andExpect(jsonPath("rank", is(nullValue())));

	}
}
