package com.api.cv.services.offer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.api.cv.dto.OfferDto;

import com.api.cv.mappers.OfferMapper;
import com.api.cv.repositories.OfferRepository;

import lombok.RequiredArgsConstructor;

@Service

public class OfferService implements IOfferService {

	

	private final OfferRepository repo;
	
	private final OfferMapper offerMapper;
	
	

	
	
	public OfferService(OfferRepository repo, OfferMapper offerMapper) {
		
		this.repo = repo;
		this.offerMapper = offerMapper;
	}

	@Override
	public OfferDto create(OfferDto offerDto) {
		
	System.out.println("creation service");
		
		
		return offerMapper.EntityToDto(repo.save(offerMapper.DtoToEntity(offerDto)));
	}
	
	
	
	@Override
	public OfferDto update(OfferDto offerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OfferDto> select() {
		// TODO Auto-generated method stub
		return null;
	}

}
