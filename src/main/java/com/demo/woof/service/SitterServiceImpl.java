package com.demo.woof.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;
import com.demo.woof.exception.ResourceNotFoundException;
import com.demo.woof.repo.SitterRepo;

@Service
public class SitterServiceImpl implements SitterService {

	@Autowired
	private SitterRepo sitterRepo;	
	@Autowired
	private SitterGradingService gradingService;
	@Autowired
	private StayService stayService;
	
	@Override
	public Page<Sitter> getSitters(Pageable pageable) {
		return sitterRepo.findAll(pageable);
	}

	@Override
	public Page<Sitter> getSittersByMinRating(Pageable pageable, int rating) {
		return sitterRepo.findByMinRating(pageable, rating);
	}
	
	@Override
	public Sitter getSitterById(long id) {
		return sitterRepo.findById(id).map(existingSitter -> {
			return existingSitter;
		}).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
	}
	
	@Override
	public Sitter createSitter(Sitter sitter) {		
		BigDecimal sitterScore = gradingService.calculateScore(sitter);
		sitter.setScore(sitterScore);
		sitter.setRank(sitterScore); // rank = sitter score when there's no stay		
		return sitterRepo.save(sitter);
	}

	/*
	 * SitterService is the only service in charge of the update of 
	 * sitter score, avg rating and rank. 
	 */
	@Override
	public Sitter updateSitter(long id, Sitter sitter) {
			
		Optional<Sitter> optSitter = sitterRepo.findById(id);
		
		if (!optSitter.isPresent()) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
		
		Sitter currentSitter = optSitter.get();
		String currentName = currentSitter.getName();
		String currentEmail = currentSitter.getEmail();
		String currentPhone = currentSitter.getPhone();
		String currentImageUrl = currentSitter.getImageUrl();
		
		String newName = sitter.getName();
		String newEmail = sitter.getEmail();
		String newPhone = sitter.getPhone();
		String newImageUrl = sitter.getImageUrl();

		if (newName != null && !newName.equals(currentName)) {
			currentSitter.setName(newName);		
			currentSitter.setScore(gradingService.calculateScore(currentSitter));
		}

		if (newEmail != null && !newEmail.equals(currentEmail)) {
			currentSitter.setEmail(newEmail);				
		}
		
		if (newPhone != null && !newPhone.equals(currentPhone)) {
			currentSitter.setPhone(newPhone);				
		}
		
		if (newImageUrl != null && !newImageUrl.equals(currentImageUrl)) {
			currentSitter.setImageUrl(newImageUrl);				
		}
		
		// Re-calculate avg rating when there's an update to sitter
		List<Stay> stays = stayService.getStaysBySitterId(id);
		currentSitter.setAvgRating(gradingService.calculateAvgRating(stays));	
		currentSitter.setRank(gradingService.calculateRank(currentSitter, stays));	
			
		return sitterRepo.save(currentSitter);
	}

	@Override
	public void deleteSitter(long id) {
		sitterRepo.deleteById(id);
	}

}
