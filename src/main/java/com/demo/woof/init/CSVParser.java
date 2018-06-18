package com.demo.woof.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.demo.woof.Config;
import com.demo.woof.domain.Owner;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;

public final class CSVParser {

	private CSVParser() {}

	private static List<Sitter> sitters = new ArrayList<>();
	private static List<Owner> owners = new ArrayList<>();
	private static List<Stay> stays = new ArrayList<>();

	public static List<Sitter> getSitters() {
		return sitters;
	}
	
	public static List<Owner> getOwners() {
		return owners;
	}
	
	public static List<Stay> getStays() {
		return stays;
	}
	
	
	public static void parse() throws IOException {		
		ClassLoader classLoader = CSVParser.class.getClassLoader();
		File file = new File(classLoader.getResource(Config.RECOVERED_DATA_FILE).getFile());		

		BufferedReader reader = new BufferedReader(new FileReader(file));		
		// Ignore the first line (headers)
		reader.readLine();
		String line;
		
		while ((line = reader.readLine()) != null) {
			parseLine(line.trim());
		}
		
		reader.close();
	}

	private static void parseLine(String line) {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		/*
		 * headers:
		 * rating,sitter_image,end_date,text,owner_image,dogs,sitter,owner,start_date,
		 * sitter_phone_numer,sitter_email,owner_phone_number,owner_email
		 */
		String[] tokens = line.split(",");
		int rating = Integer.valueOf(tokens[0]);
		String sitterImage = tokens[1];
		LocalDate endDate = LocalDate.parse(tokens[2], dateFormatter);
		String review = tokens[3];
		String ownerImage = tokens[4];
		String dogs = tokens[5];
		String sitterName = tokens[6];
		String ownerName = tokens[7];
		LocalDate startDate = LocalDate.parse(tokens[8], dateFormatter);
		String sitterPhone = tokens[9];
		String sitterEmail = tokens[10];
		String ownerPhone = tokens[11];
		String ownerEmail = tokens[12];
		
		Sitter sitter = new Sitter(sitterName, sitterEmail, sitterPhone, sitterImage);
		sitters.add(sitter);
		
		Owner owner = new Owner(ownerName, ownerEmail, ownerPhone, ownerImage);
		owners.add(owner);
		
		Stay stay = new Stay(startDate, endDate, owner, dogs, sitter, rating, review);
		stays.add(stay);
	}
	
}
