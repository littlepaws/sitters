package com.demo.woof.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.woof.domain.Owner;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;
import com.demo.woof.exception.ResourceNotFoundException;
import com.demo.woof.repo.StayRepo;

@Service
public class StayServiceImpl implements StayService {

	@Autowired
	private StayRepo stayRepo;
	@Autowired
	private SitterService sitterService;
	
	@Override
	public Stay createStay(Stay stay) {
		// --- Store Stay ---
		Stay savedStay =  stayRepo.save(stay);		
		
		// --- Update Sitter ---
		Sitter sitter = savedStay.getSitter();
		sitterService.updateSitter(sitter.getId(), sitter);
		
		return savedStay;
	}

	@Override
	public Stay getStayById(long id) {
		return stayRepo.findById(id).map(existingStay -> {
			return existingStay;
		}).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
	}

	@Override
	public Stay updateStay(long id, Stay stay) {
		
		Optional<Stay> optStay = stayRepo.findById(id);
		
		if (!optStay.isPresent()) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
		
		Stay currentStay = optStay.get();
		LocalDate currentStartDate = currentStay.getStartDate();
		LocalDate currentEndDate = currentStay.getEndDate();
		Owner currentOwner = currentStay.getOwner();
		String currentDogs = currentStay.getDogs();
		Sitter currentSitter = currentStay.getSitter();
		String currentReview = currentStay.getReview();
		int currentRating = currentStay.getRating();
		
		LocalDate newStartDate = stay.getStartDate();
		LocalDate newEndDate = stay.getEndDate();
		Owner newOwner = stay.getOwner();
		String newDogs = stay.getDogs();
		Sitter newSitter = stay.getSitter();
		String newReview = stay.getReview();
		int newRating = stay.getRating();
		
		boolean sitterUpdate = false;
		boolean ratingUpdate = false;
		
		if (newStartDate != null && !newStartDate.equals(currentStartDate)) {
			currentStay.setStartDate(newStartDate);			
		}
		
		if (newEndDate != null && !newEndDate.equals(currentEndDate)) {
			currentStay.setEndDate(newEndDate);				
		}
		
		if (newOwner!= null && !newOwner.equals(currentOwner)) {
			currentStay.setOwner(newOwner);				
		}
		
		if (newDogs != null && !newDogs.equals(currentDogs)) {
			currentStay.setDogs(newDogs);			
		}
		
		if (newSitter != null && !newSitter.equals(currentSitter)) {
			currentStay.setSitter(newSitter);
			sitterUpdate = true;
		}
		
		if (newReview != null && !newReview.equals(currentReview)) {
			currentStay.setReview(newReview);			
		}
		
		if (newRating != currentRating) {
			currentStay.setRating(newRating);
			ratingUpdate = true;
		}
		
		// --- Save Stay ---
		Stay savedStay = stayRepo.save(currentStay);
		
		// --- Update Sitter ---
		if (sitterUpdate || ratingUpdate) {
			Sitter sitter = savedStay.getSitter();
			sitterService.updateSitter(sitter.getId(), sitter);
		}
		
		return savedStay;
	}

	@Override
	public void deleteStay(long id) {
		stayRepo.deleteById(id);
	}

	@Override
	public List<Stay> getStaysBySitterId(long id) {
		return stayRepo.findBySitterId(id);
	}

	@Override
	public List<Stay> getStaysByOwnerId(long id) {
		return stayRepo.findByOwnerId(id);
	}

}
