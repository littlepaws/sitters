package com.demo.woof.service;

import java.util.List;

import com.demo.woof.domain.Stay;

public interface StayService {

	Stay createStay(Stay stay);
	Stay getStayById(long id);
	Stay updateStay(long id, Stay stay);
	void deleteStay(long id);
	List<Stay> getStaysBySitterId(long id);
	List<Stay> getStaysByOwnerId(long id);
}
