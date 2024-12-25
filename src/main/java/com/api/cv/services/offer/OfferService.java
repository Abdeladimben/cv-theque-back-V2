package com.api.cv.services.offer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.cv.dto.offer.OfferRequestDto;
import com.api.cv.dto.offer.OfferResponseDto;
import com.api.cv.dto.offer.OfferUpdateRequestDto;
import com.api.cv.entities.offer.Offer;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.UserNotConnectedException;
import com.api.cv.mappers.offer.OfferMapper;
import com.api.cv.mappers.offer.helpers.OfferMapperHelper;
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
    private final OfferMapperHelper offerMapperHelper;
    private final UserService userService;

    @Override
    public OfferResponseDto create(OfferRequestDto offerDto) throws ApiErrorException, RessourceDbNotFoundException {
        log.debug("creation service {}", offerDto);
        
        // Map OfferRequestDto to Offer
        Offer offer = offerMapper.DtoToEntity(offerDto);

        // Save and return the mapped OfferResponseDto
        return offerMapper.EntityToDto(offerRepository.save(offer));
    }

    @Override
    public List<OfferResponseDto> getAll() {
        return offerMapper.ListEntityToListDto(offerRepository.findAll());
    }

    @Override
    public OfferResponseDto getByUuid(String uuid) throws RessourceDbNotFoundException {
        Offer offer = offerRepository.findByUuid(uuid)
                .orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
        return offerMapper.EntityToDto(offer);
    }

    @Override
    @Transactional
    public OfferResponseDto update(OfferUpdateRequestDto offerUpdateRequestDto) throws ApiErrorException, UserNotConnectedException, RessourceDbNotFoundException {
        Offer offer = offerRepository.findByUuid(offerUpdateRequestDto.getUuid())
                .orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));

        if (!offer.getCreatedUser().getUuid().equals(userService.getUserConnected().getUuid())) {
            throw new UserNotConnectedException(ErrorCode.A403);
        }

        offerMapper.updateEntityFromDto(offerUpdateRequestDto, offer);
        Offer updatedOffer = offerRepository.save(offer);
        return offerMapper.EntityToDto(updatedOffer);
    }

    @Override
    @Transactional
    public void delete(String uuid) throws RessourceDbNotFoundException {
        Offer offer = offerRepository.findByUuid(uuid)
                .orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
        offer.setDelete(true);
        offerRepository.save(offer);
    }

	@Override
	public Page<OfferResponseDto> getFilteredOffers(String title, String ville, Double remuneration,
			Integer dureeContrat, Pageable pageable) {
		 Page<Offer> offers = offerRepository.findFilteredOffers(title, ville, remuneration, dureeContrat, pageable);
	        return offers.map(offerMapper::EntityToDto);
	}
    
    
    
    
    
    
    
    
    
    
    
}
