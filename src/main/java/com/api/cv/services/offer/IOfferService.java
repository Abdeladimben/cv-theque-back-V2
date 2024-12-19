package com.api.cv.services.offer;

import java.util.List;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.exceptions.ApiErrorException;

import com.api.cv.exceptions.RessourceDbNotFoundException;


public interface IOfferService {
	
	List<OfferResponseDto> getAll();
	
	OfferResponseDto getByUuid(String uuid) throws RessourceDbNotFoundException;
	
	OfferResponseDto create(OfferRequestDto offerDto) throws ApiErrorException;
	
	OfferResponseDto update(OfferUpdateRequestDto offerDto) throws ApiErrorException;
	
	void delete(String uuid) throws RessourceDbNotFoundException;


}
