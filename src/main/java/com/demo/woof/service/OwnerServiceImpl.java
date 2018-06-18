package com.demo.woof.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.woof.domain.Owner;
import com.demo.woof.exception.ResourceNotFoundException;
import com.demo.woof.repo.OwnerRepo;

@Service
public class OwnerServiceImpl implements OwnerService {

	@Autowired
	private OwnerRepo ownerRepo;
	
	@Override
	public Owner createOwner(Owner owner) {
		return ownerRepo.save(owner);
	}

	@Override
	public Owner getOwnerById(long id) {
		return ownerRepo.findById(id).map(existingOwner -> {
			return existingOwner;
		}).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));
	}

	@Override
	public Owner updateOwner(long id, Owner owner) {
		
		Optional<Owner> optOwner = ownerRepo.findById(id);
		
		if (!optOwner.isPresent()) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	
		Owner currentOwner = optOwner.get();
		String currentName = currentOwner.getName();
		String currentEmail = currentOwner.getEmail();
		String currentPhone = currentOwner.getPhone();
		String currentImageUrl = currentOwner.getImageUrl();
		String currentDogs = currentOwner.getDogs();
			
		String newName = owner.getName();
		String newEmail = owner.getEmail();
		String newPhone = owner.getPhone();
		String newImageUrl = owner.getImageUrl();
		String newDogs = owner.getDogs();
		
		if (newName!= null && !newName.equals(currentName)) {
			currentOwner.setName(newName);			
		}
		
		if (newEmail != null && !newEmail.equals(currentEmail)) {
			currentOwner.setEmail(newEmail);				
		}
		
		if (newPhone != null && !newPhone.equals(currentPhone)) {
			currentOwner.setPhone(newPhone);				
		}
		
		if (newImageUrl != null && !newImageUrl.equals(currentImageUrl)) {
			currentOwner.setImageUrl(newImageUrl);				
		}
	
		if (newDogs != null && !newDogs.equals(currentDogs)) {
			currentOwner.setDogs(newDogs);				
		}
	
		return ownerRepo.save(currentOwner);
	}

	@Override
	public void deleteOwner(long id) {
		ownerRepo.deleteById(id);
	}

}
