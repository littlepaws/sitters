package com.demo.woof.service;

import static org.hamcrest.CoreMatchers.is;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.woof.Config;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;

@RunWith(SpringRunner.class)
public class SitterGradingServiceTest {

	@TestConfiguration
	static class SitterGradingServiceImplTestContextConfiguration {
		@Bean
		public SitterGradingService sitterGradingService() {
			return new SitterGradingServiceImpl();
		}
	}
	
	@Autowired
	private SitterGradingService service;
	
	@Test
	public void testCalculateScore() {		
		Sitter sitter = new Sitter("Stephanie A. Long", "", "", "");
		BigDecimal actualScore = service.calculateScore(sitter);
		BigDecimal expectedScore = new BigDecimal(5*11/26.0f)
				.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
		
		assertThat(actualScore, is(expectedScore));
	}
	
	@Test
	public void testCalculateScoreNameHasSpecialChars() {
		Sitter sitter = new Sitter("abc? *#$@12 rin", "", "", "");
		BigDecimal actualScore = service.calculateScore(sitter);
		BigDecimal expectedScore = new BigDecimal(5*6/26.0f)
				.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
		
		assertThat(actualScore, is(expectedScore));
	}
	
	@Test
	public void testCalculateAvgRating() {
		
		int rate1 = 2, rate2 = 1, rate3 = 3;
		Stay stay1 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay2 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay3 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate3);
		List<Stay> stays = new ArrayList<>();
		stays.add(stay1);
		stays.add(stay2);
		stays.add(stay3);
		
		BigDecimal actualAvgRating = service.calculateAvgRating(stays);
		BigDecimal expectedAvgRating = new BigDecimal((rate1+rate2+rate3)/3.0f)
				.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);

		assertThat(actualAvgRating, is(expectedAvgRating));
	}
	
	@Test
	public void testCalculateAvgRatingZeroStay() {
		
		List<Stay> stays = new ArrayList<>();
		
		BigDecimal actualAvgRating = service.calculateAvgRating(stays);
		BigDecimal expectedAvgRating = new BigDecimal(0.00);

		assertThat(actualAvgRating, is(expectedAvgRating));
	}
	
	@Test
	public void testCalculateRank() {

		BigDecimal sitterScore = new BigDecimal(3.00);
		Sitter sitter = new Sitter(null, null, null, null);
		sitter.setScore(sitterScore);
		
		int rate1 = 2, rate2 = 1, rate3 = 3;
		Stay stay1 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay2 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay3 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate3);
		List<Stay> stays = new ArrayList<>();
		stays.add(stay1);
		stays.add(stay2);
		stays.add(stay3);
		
		BigDecimal actualRank = service.calculateRank(sitter, stays);
		BigDecimal avgRating = new BigDecimal((rate1+rate2+rate3)/3.0f);	
		BigDecimal expectedRank = getExpectedRank(stays.size(), avgRating, sitterScore);
				
		assertThat(actualRank, is(expectedRank));
	}
	
	@Test
	public void testCalculateRankZeroStay() {

		BigDecimal sitterScore = new BigDecimal(3.00);
		Sitter sitter = new Sitter(null, null, null, null);
		sitter.setScore(sitterScore);

		List<Stay> stays = new ArrayList<>();

		BigDecimal actualRank = service.calculateRank(sitter, stays);
		BigDecimal expectedRank = sitterScore;
				
		assertThat(actualRank, is(expectedRank));
	}
	
	@Test
	public void testCalculateRank10Stays() {

		BigDecimal sitterScore = new BigDecimal(3.00);
		Sitter sitter = new Sitter(null, null, null, null);
		sitter.setScore(sitterScore);
		
		int rate1 = 1, rate2 = 2;
		Stay stay1 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay2 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay3 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay4 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay5 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay6 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay7 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay8 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay9 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay10 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		
		List<Stay> stays = new ArrayList<>();
		stays.add(stay1);
		stays.add(stay2);
		stays.add(stay3);
		stays.add(stay4);
		stays.add(stay5);
		stays.add(stay6);
		stays.add(stay7);
		stays.add(stay8);
		stays.add(stay9);
		stays.add(stay10);
		
		BigDecimal actualRank = service.calculateRank(sitter, stays);
		BigDecimal avgRating = new BigDecimal((rate1*5+rate2*5)/10.0f);	
		BigDecimal expectedRank = getExpectedRank(stays.size(), avgRating, sitterScore);
				
		assertThat(actualRank, is(expectedRank));
	}
	
	@Test
	public void testCalculateRankMoreThan10Stays() {

		BigDecimal sitterScore = new BigDecimal(3.00);
		Sitter sitter = new Sitter(null, null, null, null);
		sitter.setScore(sitterScore);
		
		int rate1 = 1, rate2 = 2;
		Stay stay1 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay2 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay3 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay4 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay5 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate1);
		Stay stay6 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay7 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay8 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay9 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay10 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		Stay stay11 = new Stay(LocalDate.now(), LocalDate.now(), null, "bao|tamago", null, rate2);
		
		List<Stay> stays = new ArrayList<>();
		stays.add(stay1);
		stays.add(stay2);
		stays.add(stay3);
		stays.add(stay4);
		stays.add(stay5);
		stays.add(stay6);
		stays.add(stay7);
		stays.add(stay8);
		stays.add(stay9);
		stays.add(stay10);
		stays.add(stay11);
		
		BigDecimal actualRank = service.calculateRank(sitter, stays);
		BigDecimal avgRating = new BigDecimal((rate1*5+rate2*6)/11.0f);	
		BigDecimal expectedRank = avgRating
				.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
				
		assertThat(actualRank, is(expectedRank));
	}
	
	private BigDecimal getExpectedRank(int numOfStays, BigDecimal avgRating, BigDecimal sitterScore) {
		
		BigDecimal scoreWeight = new BigDecimal(10-numOfStays);
		BigDecimal ratingWeight = new BigDecimal(numOfStays);		
		
		BigDecimal expectedRank = (scoreWeight.multiply(sitterScore)
				.add(ratingWeight.multiply(avgRating))
				).divide(new BigDecimal(10))
				.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
		
		return expectedRank;
	}
}
