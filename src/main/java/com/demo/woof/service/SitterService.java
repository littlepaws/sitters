package com.demo.woof.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.woof.domain.Sitter;

public interface SitterService {

	public Sitter createSitter(Sitter sitter);
	public Sitter getSitterById(long id);
	public Page<Sitter> getSitters(Pageable pageable);
	public Page<Sitter> getSittersByMinRating(Pageable pageable, int rating);
	public Sitter updateSitter(long id, Sitter sitter);
	public void deleteSitter(long id);
	
}
