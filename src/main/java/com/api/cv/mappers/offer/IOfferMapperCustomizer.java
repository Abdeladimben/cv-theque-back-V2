package com.api.cv.mappers.offer;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.entities.Offer;
import com.api.cv.exceptions.ApiErrorException;

public interface IOfferMapperCustomizer {
	
	Offer DtoToEntity(OfferRequestDto offerDto) throws ApiErrorException;

}
