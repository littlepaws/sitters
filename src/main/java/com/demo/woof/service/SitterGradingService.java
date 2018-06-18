package com.demo.woof.service;

import java.math.BigDecimal;
import java.util.List;

import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;

public interface SitterGradingService {

	BigDecimal calculateScore(Sitter sitter);
	BigDecimal calculateAvgRating(List<Stay> stays);
	BigDecimal calculateRank(Sitter sitter, List<Stay> stays);
}
