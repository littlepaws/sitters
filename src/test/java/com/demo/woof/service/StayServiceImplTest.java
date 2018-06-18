package com.demo.woof.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.demo.woof.domain.Owner;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;
import com.demo.woof.exception.ResourceNotFoundException;
import com.demo.woof.repo.StayRepo;

@RunWith(SpringRunner.class)
public class StayServiceImplTest {

	@TestConfiguration
	static class StayServiceImplTestContextConfiguration {
		@Bean
		public StayService stayService() {
			return new StayServiceImpl();
		}
	}
	
	@MockBean
	private StayRepo stayRepo;
	
	@MockBean
	private SitterService sitterService;
	
	@Autowired
	private StayService stayService;
	
	@Test(expected=ResourceNotFoundException.class)
	public void testGetResourceNotFound() {		
		long id = 1;
		Optional<Stay> optStay = Optional.ofNullable(null);
		when(stayRepo.findById(id)).thenReturn(optStay);
		
		stayService.getStayById(id);		
		verify(stayRepo, times(1)).findById(id);
	}
	
	@Test
	public void testGetStayBySitterId() {
		long id = 1;
		List<Stay> stays = new ArrayList<>();
		when(stayRepo.findBySitterId(id)).thenReturn(stays);
		
		stayService.getStaysBySitterId(id);
		verify(stayRepo, times(1)).findBySitterId(id);
		verify(stayRepo, never()).findByOwnerId(id);	
	}
	
	@Test
	public void testUpdateStay() {
		
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		Owner owner = new Owner("Jen", "jen@test.com", "+14250000000");
		String dogs = "bao|tamago";
		Sitter sitter = new Sitter("Laura", "laura@test.com", "+14250000000");	
		String review = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. "
						+ "Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. "
						+ "Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. ";

		
		Stay stay = new Stay(startDate, endDate, owner, dogs, sitter, 3, review);
		long id = 1;
		Optional<Stay> optStay = Optional.of(stay);
			
		Sitter newSitter = new Sitter("Jon", "jon@test.com", "+14250000000");
		int newRating = 4;
		String newReview = "";
		Stay newStay = new Stay();
		newStay.setSitter(newSitter);
		newStay.setRating(newRating);
		newStay.setReview(newReview);
			
		when(stayRepo.findById(id)).thenReturn(optStay);
		when(stayRepo.save(stay)).thenReturn(stay);
		when(sitterService.updateSitter(id, newSitter)).thenReturn(newSitter);
		
		Stay updatedStay = stayService.updateStay(id, newStay);
		
		verify(stayRepo, times(1)).findById(id);
		verify(stayRepo, times(1)).save(stay);
		
		assertThat(updatedStay.getStartDate(), is(startDate));
		assertThat(updatedStay.getEndDate(), is(endDate));
		assertThat(updatedStay.getOwner(), is(owner));
		assertThat(updatedStay.getDogs(), is(dogs));
		assertThat(updatedStay.getSitter(), is(newSitter));
		assertThat(updatedStay.getRating(), is(newRating));
		assertThat(updatedStay.getReview(), is(newReview));		
	}
}
