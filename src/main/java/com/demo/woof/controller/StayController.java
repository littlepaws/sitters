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

import com.demo.woof.domain.Stay;
import com.demo.woof.service.StayService;

@RestController
@RequestMapping("/stays")
public class StayController {
	@Autowired
	private StayService stayService;
	
	@GetMapping
	public Stay getStay(@RequestParam long id) {
		return stayService.getStayById(id);
	}
	
	@PostMapping
	public Stay createStay(@Valid @RequestBody Stay stay) {
		return stayService.createStay(stay);
	}
	
	@PutMapping
	public Stay updateStay(@RequestParam long id, @Valid @RequestBody Stay stay) {
		return stayService.updateStay(id, stay);
	}
	
	@DeleteMapping
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteStay(@RequestParam long id) {
		stayService.deleteStay(id);
	}
}
