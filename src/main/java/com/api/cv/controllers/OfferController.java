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

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${endpoint.prefix}offers")
public class OfferController {

	private final IOfferService offerService;
	
	private Logger LOG=LoggerFactory.getLogger(OfferController.class);
	
	public OfferController(IOfferService offerService) {
		this.offerService = offerService;
	}
	
	@GetMapping()
	public List<OfferResponseDto> getAll() throws ApiErrorException {
		LOG.debug("request GETALL OfferDto");
		return null;
	}
	
	@GetMapping("{uuid}")	
	public OfferResponseDto getByUuid(@PathVariable String uuid) throws ApiErrorException {
		LOG.debug("request get by uuid{}",uuid);
		return null;
	}
	
	@PostMapping()	
	public OfferResponseDto create(@RequestBody OfferRequestDto offerDto ) throws ApiErrorException {
		LOG.debug("request creation OfferDto{}",offerDto);
		return offerService.create(offerDto);
	}
	
	@PutMapping()	
	public OfferResponseDto update(@RequestBody OfferUpdateRequestDto offerUpdateRequestDto ) throws ApiErrorException {
		LOG.debug("request creation offerUpdateRequestDto{}",offerUpdateRequestDto);
		return null;
	}
	
	@DeleteMapping("{uuid}")	
	public OfferResponseDto delete(@PathVariable String uuid) throws ApiErrorException {
		LOG.debug("request delete uuid{}",uuid);
		return null;
	}
	
}
