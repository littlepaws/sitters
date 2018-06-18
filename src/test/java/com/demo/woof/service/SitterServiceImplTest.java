package com.demo.woof.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
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
import com.demo.woof.repo.SitterRepo;

@RunWith(SpringRunner.class)
public class SitterServiceImplTest {
	
	@MockBean
	private SitterRepo sitterRepo;
	
	@MockBean
	private SitterGradingService gradingService;
	
	@MockBean
	private StayService stayService;
	
	@TestConfiguration
	static class SitterServiceImplTestContextConfiguration {
		@Bean
		public SitterService sitterService() {
			return new SitterServiceImpl();
		}
	}
	
	@Autowired
	private SitterService sitterService;
	
	private String name;
	private String email;
	private String phone;
	private String imageUrl;
	private String newName;
	private String newEmail;
	private String newPhone;
	
	private Sitter sitter;
	private long sitterId;
	private Optional<Sitter> optSitter;

	private List<Stay> stays;
	private BigDecimal score;
	
	@Before
	public void setup() {
		
		name = "Ali";
		email = "ali@test.com";
		phone = "+14250000000";
		imageUrl = "http://placekitten.com/g/500/500?user=338";
		sitterId = 1;
		
		sitter = new Sitter(name, email, phone, imageUrl);
		sitter.setId(sitterId);
		optSitter = Optional.of(sitter);
		
		newName = "Mary";
		newEmail = "mary@test.com";
		newPhone = "+14250000001";
		
		Owner owner = new Owner("Jen", "jen@test.com", "+14250000000");
		
		Stay stay = new Stay(LocalDate.now(), LocalDate.now(), owner, "bao|tamago", sitter, 2);
		stays = new ArrayList<>();
		stays.add(stay);
		
		score = new BigDecimal(2.5);
	}
	
	@Test
	public void testCreateSitter() {
		
		when(gradingService.calculateScore(sitter)).thenReturn(score);
		when(sitterRepo.save(sitter)).thenReturn(sitter);		
		
		Sitter createdSitter = sitterService.createSitter(sitter);
		
		verify(gradingService, times(1)).calculateScore(sitter);
		verify(sitterRepo, times(1)).save(sitter);

		assertThat(createdSitter.getName(), is(name));
		assertThat(createdSitter.getEmail(), is(email));
		assertThat(createdSitter.getPhone(), is(phone));
		assertThat(createdSitter.getImageUrl(), is(imageUrl));
		assertThat(createdSitter.getScore(), is(score));
		assertThat(createdSitter.getAvgRating(), is(new BigDecimal(0.00)));
		assertThat(createdSitter.getRank(), is(score));
	}
	
	@Test
	public void testUpdateSitterNoNameChange() {
			
		sitter.setScore(score);
		
		Sitter newSitter = new Sitter();
		newSitter.setEmail(newEmail);
		
		when(stayService.getStaysBySitterId(sitterId)).thenReturn(stays);
		when(gradingService.calculateRank(sitter, stays)).thenReturn(score);
		when(gradingService.calculateAvgRating(stays)).thenReturn(score);
		when(sitterRepo.findById(sitterId)).thenReturn(optSitter);	
		when(sitterRepo.save(sitter)).thenReturn(sitter);		
		
		Sitter updatedSitter = sitterService.updateSitter(sitterId, newSitter);
		
		verify(gradingService, never()).calculateScore(sitter);
		verify(gradingService, times(1)).calculateAvgRating(stays);
		verify(gradingService, times(1)).calculateRank(sitter, stays);
		verify(sitterRepo, times(1)).findById(sitterId);
		verify(sitterRepo, times(1)).save(sitter);

		assertThat(updatedSitter.getName(), is(name));
		assertThat(updatedSitter.getEmail(), is(newEmail));
		assertThat(updatedSitter.getPhone(), is(phone));
		assertThat(updatedSitter.getImageUrl(), is(imageUrl));
		assertThat(updatedSitter.getScore(), is(score));
		assertThat(updatedSitter.getAvgRating(), is(score));
		assertThat(updatedSitter.getRank(), is(score));
	}
	
	@Test
	public void testUpdateSitterNameChange() {
				
		Sitter newSitter = new Sitter(newName, newEmail, newPhone);
		
		when(stayService.getStaysBySitterId(sitterId)).thenReturn(stays);
		when(gradingService.calculateScore(sitter)).thenReturn(score);
		when(gradingService.calculateRank(sitter, stays)).thenReturn(score);
		when(gradingService.calculateAvgRating(stays)).thenReturn(score);
		when(sitterRepo.findById(sitterId)).thenReturn(optSitter);	
		when(sitterRepo.save(sitter)).thenReturn(sitter);		
		
		Sitter updatedSitter = sitterService.updateSitter(sitterId, newSitter);
		
		verify(gradingService, times(1)).calculateScore(sitter);
		verify(gradingService, times(1)).calculateAvgRating(stays);
		verify(gradingService, times(1)).calculateRank(sitter, stays);
		verify(sitterRepo, times(1)).findById(sitterId);
		verify(sitterRepo, times(1)).save(sitter);

		assertThat(updatedSitter.getName(), is(newName));
		assertThat(updatedSitter.getEmail(), is(newEmail));
		assertThat(updatedSitter.getPhone(), is(newPhone));
		assertThat(updatedSitter.getImageUrl(), is(imageUrl));
		assertThat(updatedSitter.getScore(), is(score));
		assertThat(updatedSitter.getAvgRating(), is(score));
		assertThat(updatedSitter.getRank(), is(score));
	}
}
