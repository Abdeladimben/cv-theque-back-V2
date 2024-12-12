package com.api.cv.services.offer;

import java.util.List;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.exceptions.RessourceDbNotFound;


public interface IOfferService {
	
	List<OfferResponseDto> getAll();
	
	OfferResponseDto getByUuid(String uuid) throws RessourceDbNotFound;
	
	OfferResponseDto create(OfferRequestDto offerDto) throws ApiErrorException;
	
	OfferResponseDto update(OfferUpdateRequestDto offerDto) throws ApiErrorException;
	
	void delete(String uuid) throws RessourceDbNotFound;


}
