package com.api.cv.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.OfferDto;
import com.api.cv.mappers.OfferMapper;
import com.api.cv.services.offer.IOfferService;

import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("api/offers")
public class OfferController {

	
	private final IOfferService service;
	
	private Logger LOG=LoggerFactory.getLogger(OfferController.class);
	

	
public OfferController(IOfferService service) {
		
		this.service = service;
	
	}





@PostMapping	
public OfferDto create(@RequestBody OfferDto offerDto ) {
	LOG.debug("request creation OfferDto{}",offerDto);
	
	return service.create(offerDto);
}
	
}
