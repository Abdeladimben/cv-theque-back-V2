package com.api.cv.mappers;

import org.mapstruct.Mapper;

import com.api.cv.dto.OfferDto;
import com.api.cv.entities.Offer;



@Mapper(componentModel = "spring")
public interface OfferMapper{
	
	Offer DtoToEntity(OfferDto offerDto);
	OfferDto EntityToDto(Offer offer);
	
	
	

}
