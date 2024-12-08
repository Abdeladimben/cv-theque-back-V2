package com.api.cv.mappers.offer;

import org.springframework.stereotype.Component;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.Offer;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.services.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OfferMapperCustomizer implements IOfferMapperCustomizer{
	
	private final OfferMapper offerMapper;
	
	private final UserService userService;

	@Override
	public Offer DtoToEntity(OfferRequestDto offerDto) throws ApiErrorException {
		// TODO Auto-generated method stub
		Offer offer = offerMapper.DtoToEntity(offerDto);
		offer.setCreatedUser(userService.getUserConnected());
		offer.setStatus("ACTIVE");
		return offer;
	}



}
