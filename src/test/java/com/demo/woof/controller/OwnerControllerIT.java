package com.demo.woof.controller;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.demo.woof.ValidationConst;
import com.demo.woof.domain.Owner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerIT {

	@Autowired
	ObjectMapper objMapper;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private String ownerName = "Jen";
	private String ownerEmail = "jen@test.com";
	private String ownerPhone = "+14250000000";

	@Test
	public void testCreateOwner() throws JsonProcessingException, Exception {
	
		Owner owner = new Owner(ownerName, ownerEmail, ownerPhone);	
		ResponseEntity<Owner> response = restTemplate.exchange("/owners", HttpMethod.POST, new HttpEntity<Owner>(owner), Owner.class);

		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		
		Owner respOwner = response.getBody();
		assertThat(respOwner.getName(), is(ownerName));
		assertThat(respOwner.getEmail(), is(ownerEmail));
		assertThat(respOwner.getPhone(), is(ownerPhone));
	}
	
	@Test
	public void testUpdateOwner() throws JsonProcessingException, Exception {
	
		// Create owner
		Owner owner = new Owner(ownerName, ownerEmail, ownerPhone);	
		ResponseEntity<Owner> response = restTemplate.exchange("/owners", HttpMethod.POST, new HttpEntity<Owner>(owner), Owner.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		
		// Update owner
		long id = response.getBody().getId();
		String updatedEmail = "jen2@test.com";
		owner.setEmail(updatedEmail);

		response = restTemplate.exchange("/owners?id="+id, HttpMethod.PUT, new HttpEntity<Owner>(owner), Owner.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		
		Owner respOwner = response.getBody();
		assertThat(respOwner.getName(), is(ownerName));
		assertThat(respOwner.getEmail(), is(updatedEmail));
		assertThat(respOwner.getPhone(), is(ownerPhone));		
	}
	
	@Test
	public void testCreateStayInvalidEntityField() throws JsonProcessingException, Exception {
	
		Owner owner = new Owner(ownerName, "invalidEmailFormat", "4251000000");	
		ResponseEntity<String> response = restTemplate.exchange("/owners", HttpMethod.POST, new HttpEntity<Owner>(owner), String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		
		String expectedErrMsg1 = ValidationConst.FIELD_ERR_INVALID_EMAIL_FORMAT;
		String expectedErrMsg2 = ValidationConst.FIELD_ERR_INVALID_PHONE_FORMAT;
		
		JSONObject json = new JSONObject(response.getBody());
		System.out.println(json.get("errors").toString());
		List<ErrorDetail> errors = objMapper.readValue(json.get("errors").toString(), new TypeReference<List<ErrorDetail>>() {});
		assertThat(errors.size(), is(2));
		assertThat(errors.get(0).getMsg(), anyOf(equalTo(expectedErrMsg1), equalTo(expectedErrMsg2)));
		assertThat(errors.get(1).getMsg(), anyOf(equalTo(expectedErrMsg1), equalTo(expectedErrMsg2)));	
	}
	
}
