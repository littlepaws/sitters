package com.demo.woof.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.demo.woof.Config;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;

@Service
public class SitterGradingServiceImpl implements SitterGradingService {

	private static final Logger logger = LogManager.getLogger(SitterGradingServiceImpl.class);
	
	/**
	 * Formula: 
	 * b = number of stays
	 * a = 10 - number of stays
	 * 
	 * number of stays < 10:
	 * rank = (a * sitterScore + b * avgRating ) / 10
	 * 
	 * number of stays >= 10:
	 * rank = avgRating
	 */
	@Override
	public BigDecimal calculateRank(Sitter sitter, List<Stay> stays) {	
		
		int numOfStays = stays.size();
		BigDecimal sitterScore = sitter.getScore();
		BigDecimal avgRating = calculateAvgRating(stays);
		BigDecimal scoreWeight = new BigDecimal(10-numOfStays);
		BigDecimal ratingWeight = new BigDecimal(numOfStays);
		
		if (numOfStays >= 10) {
			return avgRating;
		}

		logger.debug("sitter score: " + sitterScore + ", weight: " + scoreWeight 
						+ "; avg rating: " + avgRating + ", weight: " + ratingWeight);
		
		BigDecimal rank = (scoreWeight.multiply(sitterScore)
							.add(ratingWeight.multiply(avgRating))
						   ).divide(new BigDecimal(10));
		
		rank.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
		return rank;
	}
	

	@Override
	public BigDecimal calculateAvgRating(List<Stay> stays) {
		
		if (stays.isEmpty()) {
			return new BigDecimal(0);
		}
		
		float totalRating = 0.0f;
		
		for (Stay stay: stays) {
			totalRating += stay.getRating();
		}
		
		int numOfStays = stays.size();
		float avg = totalRating/numOfStays;
		logger.debug("total rating: " + totalRating + ", num of stays: " + numOfStays);
		
		BigDecimal avgRating = new BigDecimal(avg)
					.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
		return avgRating;
	}
	
	@Override
	public BigDecimal calculateScore(Sitter sitter) {
		
		String name = sitter.getName().toLowerCase();		
		Set<Character> distinctLetters = new HashSet<>();
		
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c >= 97 && c <=122) {
				distinctLetters.add(c); 
			}
		}
		
		logger.debug("distinct chars: " + distinctLetters);
		float score = 5*distinctLetters.size()/26.0f;
		
		BigDecimal sitterScore = new BigDecimal(score)
				.setScale(Config.DECIMAL_NUMBER_SCALE, Config.DECIMAL_NUMBER_ROUNDING);
		return sitterScore;
	}


}
