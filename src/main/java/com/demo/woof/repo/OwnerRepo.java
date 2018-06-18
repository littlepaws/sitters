package com.demo.woof.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.woof.domain.Owner;

@Repository
public interface OwnerRepo extends JpaRepository<Owner,Long> {

}
