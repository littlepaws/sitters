package com.demo.woof.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.*;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.demo.woof.domain.Owner;
import com.demo.woof.exception.ResourceNotFoundException;
import com.demo.woof.repo.OwnerRepo;

@RunWith(SpringRunner.class)
public class OwnerServiceImplTest {

	@MockBean
	private OwnerRepo ownerRepo;
	
	@TestConfiguration
	static class OwnerServiceImplTestContextConfiguration {
		@Bean
		public OwnerService ownerService() {
			return new OwnerServiceImpl();
		}
	}
	
	@Autowired
	private OwnerService ownerService;	

	@Test
	public void testUpdateOwner() {
		
		String name = "Jen";
		String email = "jen@test.com";
		String phone = "+14250000000";
		String imageUrl = "http://placekitten.com/g/500/500?user=338";
		long id = 1;
		
		Owner owner = new Owner(name, email, phone, imageUrl);
		owner.setId(id);
		Optional<Owner> optOwner = Optional.of(owner);
		
		String newEmail = "jen2@test.com";
		String newPhone = "+14250000001";
		Owner newOwner = new Owner();
		newOwner.setEmail(newEmail);
		newOwner.setPhone(newPhone);
		
		when(ownerRepo.findById(id)).thenReturn(optOwner);
		when(ownerRepo.save(owner)).thenReturn(owner);
	
		Owner updatedOwner = ownerService.updateOwner(id, newOwner);
		
		verify(ownerRepo, times(1)).findById(id);
		verify(ownerRepo, times(1)).save(owner);
		
		assertThat(updatedOwner.getName(), is(equalTo(name)));
		assertThat(updatedOwner.getEmail(), is(equalTo(newEmail)));
		assertThat(updatedOwner.getPhone(), is(equalTo(newPhone)));
		assertThat(updatedOwner.getImageUrl(), is(equalTo(imageUrl)));
		assertThat(updatedOwner.getDogs(), is(equalTo(owner.getDogs())));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateOwnerNotFound() {
		
		String name = "Jen";
		String email = "jen@test.com";
		String phone = "+14250000000";
		String imageUrl = "http://placekitten.com/g/500/500?user=338";
		long id = 1;
		
		Owner owner = new Owner(name, email, phone, imageUrl);
		owner.setId(id);
		Optional<Owner> optOwner = Optional.ofNullable(null);
		
		given(ownerRepo.findById(id)).willReturn(optOwner);
	
		ownerService.updateOwner(id, owner);
		
		verify(ownerRepo, times(1)).findById(id);
		verify(ownerRepo, never()).save(owner);
	}
	
}
