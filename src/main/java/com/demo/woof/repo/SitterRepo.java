package com.demo.woof.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.demo.woof.domain.Sitter;

@Repository
public interface SitterRepo extends JpaRepository<Sitter,Long> {

	@Query(value="SELECT * FROM sitters WHERE avg_rating >= ?1", nativeQuery=true)
	Page<Sitter> findByMinRating(Pageable pageable, int rating);

}
