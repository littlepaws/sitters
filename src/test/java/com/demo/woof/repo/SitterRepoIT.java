package com.demo.woof.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.woof.domain.Sitter;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SitterRepoIT {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SitterRepo repo;
	
	@Test
	public void testFindByMinRatingReturnsCorrectSitters() {
		
		Sitter sitter1 = new Sitter("Laura", "laura@test.com", "+14250000000");
		sitter1.setAvgRating(new BigDecimal(3.2));
		entityManager.persist(sitter1);
		
		Sitter sitter2 = new Sitter("Cindy", "cindy@test.com", "+14250000000");
		sitter2.setAvgRating(new BigDecimal(2.6));
		entityManager.persist(sitter2);
		
		Sitter sitter3 = new Sitter("Linda", "linda@test.com", "+14250000000");
		sitter3.setAvgRating(new BigDecimal(4.8));
		entityManager.persist(sitter3);
		
		entityManager.flush();
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<Sitter> sittersFound = repo.findByMinRating(pageable, 3);
		
		assertThat(sittersFound.getNumberOfElements(), is(2));
		assertThat(sittersFound.getContent(), contains(sitter1, sitter3));
	}
}
