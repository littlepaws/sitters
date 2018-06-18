package com.demo.woof.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.woof.domain.Owner;
import com.demo.woof.service.OwnerService;

@RestController
@RequestMapping("/owners")
public class OwnerController {

	@Autowired
	private OwnerService ownerService;
	
	@GetMapping
	public Owner getOwner(@RequestParam long id) {
		return ownerService.getOwnerById(id);
	}
	
	@PostMapping
	@ResponseStatus(value=HttpStatus.CREATED)
	public Owner createOwner(@Valid @RequestBody Owner owner) {
		return ownerService.createOwner(owner);
	}
	
	@PutMapping
	public Owner updateOwner(@RequestParam long id, @Valid @RequestBody Owner owner) {
		return ownerService.updateOwner(id, owner);
	}
	
	@DeleteMapping
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteOwner(@RequestParam long id) {
		ownerService.deleteOwner(id);
	}
	
}
