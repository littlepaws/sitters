package com.demo.woof.repo;

import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.woof.domain.Owner;
import com.demo.woof.domain.Sitter;
import com.demo.woof.domain.Stay;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StayRepoIT {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired 
	private StayRepo repo;
	
	private Owner owner1, owner2;
	private Sitter sitter;
	private Stay stay1, stay2;
	
	@Before
	public void setUpStay() {
		
		Owner newOwner1 = new Owner("Jen", "jen@test.com", "+14250000000");
		Owner newOwner2 = new Owner("Jon", "jon@test.com", "+14250000000");
		Sitter newSitter = new Sitter("Laura", "laura@test.com", "+14250000000");	
		
		Stay newStay1 = new Stay(LocalDate.now(), LocalDate.now(), newOwner1, "bao|tamago", newSitter, 2);
		Stay newStay2 = new Stay(LocalDate.now(), LocalDate.now(), newOwner2, "feddie", newSitter, 3);
		
		owner1 = entityManager.persist(newOwner1);
		owner2 = entityManager.persist(newOwner2);
		sitter = entityManager.persist(newSitter);
		stay1 = entityManager.persist(newStay1);
		stay2 = entityManager.persist(newStay2);
	}
	
	@Test
	public void testFindBySitterIdReturnsCorrectStays() {		
		
		List<Stay> actualStays = repo.findBySitterId(sitter.getId());
		assertThat(actualStays, hasSize(2));
		assertThat(actualStays, contains(stay1, stay2));
	}
	
	@Test
	public void testFindByOwnerIdReturnsCorrectStays() {
		
		List<Stay> actualStays1 = repo.findByOwnerId(owner1.getId());
		assertThat(actualStays1, hasSize(1));
		assertThat(actualStays1, contains(stay1));
		
		List<Stay> actualStays2 = repo.findByOwnerId(owner2.getId());
		assertThat(actualStays2, hasSize(1));
		assertThat(actualStays2, contains(stay2));
	}
}
