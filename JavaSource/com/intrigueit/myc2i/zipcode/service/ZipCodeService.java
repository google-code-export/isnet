package com.intrigueit.myc2i.zipcode.service;

import java.util.List;

import com.intrigueit.myc2i.zipcode.domain.ZipCode;

public interface ZipCodeService {

	public ZipCode findById( String id);

	public List<ZipCode> findAll();
	
	public List<ZipCode> findByState(String state);
	
	public List<ZipCode> findByCity(String city);
}
