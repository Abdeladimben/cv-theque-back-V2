package com.api.cv.mappers.offer;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.offer.Offer;
import com.api.cv.helpers.StatusMapperHelper;

@Mapper(componentModel = "spring", uses = {StatusMapperHelper.class})

public interface OfferMapper{
	
	
    @Mapping(target = "offerStatus", source = "statusCode")
    @Mapping(target = "contractType", source = "contractTypeCode")
	Offer DtoToEntity(OfferRequestDto offerDto);
	
	OfferResponseDto EntityToDto(Offer offer);
	
	List<OfferResponseDto> ListEntityToListDto(List<Offer> listOffer);
	
	Offer updateDtoToEntity(OfferUpdateRequestDto offerUpdateRequestDto);
	

    void updateEntityFromDto(OfferUpdateRequestDto offerUpdateRequestDto, @MappingTarget Offer offer);

	
}
