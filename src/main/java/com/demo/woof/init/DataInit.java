package com.demo.woof.init;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.woof.domain.Owner;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;
import com.demo.woof.service.OwnerService;
import com.demo.woof.service.SitterService;
import com.demo.woof.service.StayService;

@Component
public class DataInit {

	@Autowired
	private OwnerService ownerService; 
	@Autowired
	private SitterService sitterService;
	@Autowired
	private StayService stayService;
	
	/**
	 * To dump data from csv to db. This method will be run after beans are wired.
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void run() throws IOException {

		CSVParser.parse();
		
		List<Owner> owners = CSVParser.getOwners();	
		for (Owner owner: owners) {
			ownerService.createOwner(owner);
		}
		
		List<Sitter> sitters = CSVParser.getSitters();
		for (Sitter sitter: sitters) {
			sitterService.createSitter(sitter);
		}
		
		List<Stay> stays = CSVParser.getStays();
		for (Stay stay: stays) {
			stayService.createStay(stay);
		}

	}
	
}
