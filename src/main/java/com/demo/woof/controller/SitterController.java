package com.demo.woof.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.demo.woof.Config;
import com.demo.woof.domain.Sitter;
import com.demo.woof.service.SitterService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/sitters")
public class SitterController {

	@Autowired
	private SitterService sitterService;
	
	@GetMapping(params={"page", "minrating"})
	public Page<Sitter> get(@RequestParam(required=false, defaultValue="0") int page,
							@RequestParam(required=false, defaultValue="0") int minrating) {

		int limit = Config.ITEM_PER_PAGE;
		Sort sort = new Sort(Sort.Direction.DESC, "rank");
		Pageable pageable = PageRequest.of(page, limit, sort);
		Page<Sitter> sitters = sitterService.getSittersByMinRating(pageable, minrating);
		return sitters;
	}
	
	@GetMapping
	public Sitter get(@RequestParam long id) {
		return sitterService.getSitterById(id);
	}
	
	@PostMapping
	@ResponseStatus(value=HttpStatus.CREATED)
	public Sitter create(@Valid @RequestBody Sitter sitter) {
		return sitterService.createSitter(sitter);
	}
	
	@PutMapping()
	public Sitter update(@RequestParam long id, @Valid @RequestBody Sitter sitter) {
		return sitterService.updateSitter(id, sitter);
	}
	
	@DeleteMapping
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void delete(@RequestParam long id) {
		sitterService.deleteSitter(id);
	}
	
}