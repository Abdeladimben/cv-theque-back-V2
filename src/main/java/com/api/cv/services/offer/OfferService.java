package com.api.cv.services.offer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.offer.Offer;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.ApiErrorException;

import com.api.cv.exceptions.RessourceDbNotFound;
import com.api.cv.mappers.offer.OfferMapper;
import com.api.cv.mappers.offer.IOfferMapperCustomizer;
import com.api.cv.repositories.OfferRepository;
import com.api.cv.services.user.UserService;

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
	private final UserService userService;
	
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
	public OfferResponseDto getByUuid(String uuid) throws RessourceDbNotFound {		
	  Offer offer = offerRepository.findByUuid(uuid)
	            .orElseThrow(() -> {
	                return new RessourceDbNotFound(ErrorCode.A400);
		            });
		    return offerMapper.EntityToDto(offer);
	}
	
	@Override
	@Transactional
	public OfferResponseDto update(OfferUpdateRequestDto offerUpdateRequestDto) throws ApiErrorException  {
        Offer offer = offerRepository.findByUuid(offerUpdateRequestDto.getUuid())
                .orElseThrow(() -> new RessourceDbNotFound(ErrorCode.A400));
        
        if (!offer.getCreatedUser().getUuid().equals(userService.getUserConnected().getUuid())) {
            throw new RuntimeException("not authorized.");
        }
        
        System.out.println(offer.getCreatedUser().getUuid()+""+userService.getUserConnected().getUuid());
        
        offerMapper.updateEntityFromDto(offerUpdateRequestDto, offer);
        Offer updatedOffer = offerRepository.save(offer);
        return offerMapper.EntityToDto(updatedOffer);
	}

	@Override
	@Transactional
	public void delete(String uuid) throws RessourceDbNotFound {
	    Offer offer = offerRepository.findByUuid(uuid)
	            .orElseThrow(() -> new RessourceDbNotFound(ErrorCode.A400));	
	    offer.setDelete(true);
	    offerRepository.save(offer);
	}



}
