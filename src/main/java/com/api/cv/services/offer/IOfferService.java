package com.api.cv.services.offer;

import java.util.List;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.exceptions.ApiErrorException;


public interface IOfferService {
	
	List<OfferResponseDto> getAll();
	
	OfferResponseDto getByUuid(String uuid);
	
	OfferResponseDto create(OfferRequestDto offerDto) throws ApiErrorException;
	
	OfferResponseDto update(OfferUpdateRequestDto offerDto);
	
	void delete(String uuid);


}
