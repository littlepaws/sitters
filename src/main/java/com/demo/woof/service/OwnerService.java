package com.demo.woof.service;

import com.demo.woof.domain.Owner;

public interface OwnerService {

	Owner createOwner(Owner owner);
	Owner getOwnerById(long id);
	Owner updateOwner(long id, Owner owner);
	void deleteOwner(long id);
}
