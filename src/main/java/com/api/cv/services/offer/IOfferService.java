package com.api.cv.services.offer;

import java.util.List;

import com.api.cv.dto.OfferDto;


public interface IOfferService {
	
	OfferDto create(OfferDto offerDto);
	
	OfferDto update(OfferDto offerDto);
	
	void delete(Long id);
	
	List<OfferDto> select();

}
