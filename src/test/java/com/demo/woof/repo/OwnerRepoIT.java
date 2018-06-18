package com.demo.woof.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.woof.domain.Owner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OwnerRepoIT {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private OwnerRepo repo;
	
	/**
	 * This test only serves as a sanity check.
	 * It doesn't provide much value since it's
	 * testing a method from the Spring library.
	 */
	@Test
	public void testSaveUpdatesExistingOwner() {
	
		String name = "Jen";
		String updatedEmail = "jen_updated@test.com";
		String phone = "+14250000000";
		
		Owner owner = new Owner(name, "jen@test.com", phone);
		long ownerId = entityManager.persistAndFlush(owner).getId();
	
		owner.setEmail(updatedEmail);
		repo.save(owner);
		
		Owner savedOwner = entityManager.find(Owner.class, ownerId);
		
		assertThat(savedOwner.getName(), is(name));
		assertThat(savedOwner.getEmail(), is(updatedEmail));
		assertThat(savedOwner.getPhone(), is(phone));
	}
}
