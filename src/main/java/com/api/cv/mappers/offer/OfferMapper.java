package com.api.cv.mappers.offer;

import java.util.List;

import com.api.cv.entities.User;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.services.user.UserService;
import org.mapstruct.*;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.offer.Offer;
import com.api.cv.mappers.offer.helpers.OfferMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(
		componentModel = "spring",
		uses = {OfferMapperHelper.class},
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public abstract class OfferMapper {

	@Autowired
	UserService userService;

	@Mapping(source = "statusCode", target = "offerStatus")
	@Mapping(source = "contractTypeCode", target = "contractType" )
	@Mapping(target = "createdUser", expression = "java(getUserFromService())")
	public abstract Offer DtoToEntity(OfferRequestDto offerDto) throws ApiErrorException, RessourceDbNotFoundException;

	@Mapping(target = "createdUser", source = "createdUser")
	@Mapping(target = "offerStatus", source = "offerStatus")
	@Mapping(target = "contractType", source = "contractType")
	public abstract OfferResponseDto EntityToDto(Offer offer);

	public abstract List<OfferResponseDto> ListEntityToListDto(List<Offer> offers);

	@Mapping(target = "createdUser", ignore = true)
	@Mapping(source = "statusCode", target = "offerStatus")
	@Mapping(source = "contractTypeCode", target = "contractType" )
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public abstract void updateEntityFromDto(OfferUpdateRequestDto updateDto, @MappingTarget Offer offer) throws RessourceDbNotFoundException;

	protected User getUserFromService() throws ApiErrorException {
		return userService.getUserConnected();
	}
}