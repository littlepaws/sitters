package com.demo.woof.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.demo.woof.service.StayService;

@RunWith(SpringRunner.class)
@WebMvcTest(StayController.class)
public class StayControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StayService service;
		
	@Test
	public void testDeleteById() throws Exception {
		
		long id = 1;	
		doNothing().when(service).deleteStay(id);
		
		mockMvc.perform(delete("/stays?id=" + id))
		.andExpect(status().isNoContent());
	}

}
