package com.api.cv.mappers.offer;

import java.util.List;

import org.mapstruct.Mapper;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.Offer;

@Mapper(componentModel = "spring")
public interface OfferMapper{
	
	Offer DtoToEntity(OfferRequestDto offerDto);
	
	OfferResponseDto EntityToDto(Offer offer);
	
	List<OfferResponseDto> ListEntityToListDto(List<Offer> listOffer);
	
	Offer updateDtoToEntity(OfferUpdateRequestDto offerUpdateRequestDto);
	
}
