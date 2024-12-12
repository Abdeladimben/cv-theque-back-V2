package com.api.cv.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.mappers.offer.OfferMapper;
import com.api.cv.services.offer.IOfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${endpoint.prefix}offers")
public class OfferController {

	private final IOfferService offerService;
	
	private Logger LOG=LoggerFactory.getLogger(OfferController.class);
	
	public OfferController(IOfferService offerService) {
		this.offerService = offerService;
	}
	
	@Operation(summary="Get all offers endpoint",description="Get all offers exist")
	@ApiResponse(responseCode = "200",description="offers retrieved successfully")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@GetMapping()
	public List<OfferResponseDto> getAll() throws ApiErrorException {
		LOG.debug("request GETALL OfferDto");
		return offerService.getAll();
	}
	@Operation(summary="Get offer By uuid endpoint",description="Get offer By uuid endpoint")
	@ApiResponse(responseCode = "200",description="offer retrieved  successfully")
	@ApiResponse(responseCode = "400", description = "bad request")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@GetMapping("{uuid}")	
	public OfferResponseDto getByUuid(@PathVariable String uuid) throws ApiErrorException {
		LOG.debug("request get by uuid{}",uuid);
		return offerService.getByUuid(uuid);
	}
	
	@Operation(summary="create a new offer endpoint",description="Create a new offer by infos provided")
	@ApiResponse(responseCode = "200",description="offer created  successfully")
	@ApiResponse(responseCode = "400", description = "bad request")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@PostMapping()	
	public OfferResponseDto create(@RequestBody OfferRequestDto offerDto ) throws ApiErrorException {
		LOG.debug("request creation OfferDto{}",offerDto);
		return offerService.create(offerDto);
	}
	@Operation(summary="update offer endpoint",description="update an offer endpoint by infos provided")
	@ApiResponse(responseCode = "200",description="offer updated  successfully")
	@ApiResponse(responseCode = "400", description = "bad request")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@PutMapping()	
	public OfferResponseDto update(@RequestBody OfferUpdateRequestDto offerUpdateRequestDto ) throws ApiErrorException {
		LOG.debug("request creation offerUpdateRequestDto{}",offerUpdateRequestDto);
		return offerService.update(offerUpdateRequestDto);
	}
	
	@Operation(summary="delete offer endpoint",description="delete an offer endpoint by UUID")
	@ApiResponse(responseCode = "200",description="offer deleted  successfully")
	@ApiResponse(responseCode = "400", description = "invalid UUID")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@DeleteMapping("{uuid}")	
	public void delete(@PathVariable String uuid) throws ApiErrorException {
		LOG.debug("request delete uuid{}",uuid);
			offerService.delete(uuid);
	}
	
}
