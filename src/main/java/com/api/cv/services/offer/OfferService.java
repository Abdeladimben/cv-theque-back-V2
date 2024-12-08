package com.api.cv.services.offer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.Offer;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.mappers.offer.OfferMapper;
import com.api.cv.mappers.offer.IOfferMapperCustomizer;
import com.api.cv.repositories.OfferRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfferService implements IOfferService {

	private final OfferRepository offerRepository;
	
	private final OfferMapper offerMapper;
	
	private final IOfferMapperCustomizer offerMapperCustomizer;
	
	@Override
	public OfferResponseDto create(OfferRequestDto offerDto) throws ApiErrorException {
		log.debug("creation service {}",offerDto);
	
		return offerMapper.EntityToDto(offerRepository.save(offerMapperCustomizer.DtoToEntity(offerDto)));
	}

	@Override
	public List<OfferResponseDto> getAll() {
		return offerMapper.ListEntityToListDto(offerRepository.findAll());
	}

	@Override
	public OfferResponseDto getByUuid(String uuid) {
		
	
		return offerMapper.EntityToDto(offerRepository.findByUuid(uuid));
	}

	@Override
	public OfferResponseDto update(OfferUpdateRequestDto offerDto) {
		// TODO Auto-generated method stub
		return offerMapper.EntityToDto(offerRepository.save(offerMapper.updateDtoToEntity(offerDto)));

	}

	@Override
    @Transactional
	public void delete(String uuid) {

		offerRepository.deleteByUuid(uuid);
		
	}
	


}
