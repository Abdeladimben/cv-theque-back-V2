package com.api.cv.services.offer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.exceptions.ApiErrorException;

import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.UserNotConnectedException;


public interface IOfferService {
	
	List<OfferResponseDto> getAll();
	
	OfferResponseDto getByUuid(String uuid) throws RessourceDbNotFoundException;
	
	OfferResponseDto create(OfferRequestDto offerDto) throws ApiErrorException, RessourceDbNotFoundException;
	
	OfferResponseDto update(OfferUpdateRequestDto offerDto) throws ApiErrorException, UserNotConnectedException, RessourceDbNotFoundException;
	
	void delete(String uuid) throws RessourceDbNotFoundException;

	Page<OfferResponseDto> getFilteredOffers( String title,
            String ville,
            Double remuneration,
            Integer dureeContrat,
            Pageable pageable);
	
	
}
