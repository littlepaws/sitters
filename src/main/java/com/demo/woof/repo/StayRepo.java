package com.demo.woof.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.demo.woof.domain.Stay;

@Repository
public interface StayRepo extends JpaRepository<Stay,Long> {
	
	@Query(value="SELECT * FROM Stays WHERE sitter_id = ?1", nativeQuery=true)
	List<Stay> findBySitterId(long id);
	
	@Query(value="SELECT * FROM Stays WHERE owner_id = ?1", nativeQuery=true)
	List<Stay> findByOwnerId(long id);
}
