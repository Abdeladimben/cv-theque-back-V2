package com.api.cv.mappers.offer;

import com.api.cv.entities.offer.Offer;
import com.api.cv.entities.referentiel.ContractType;
import com.api.cv.entities.referentiel.OfferStatus;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.repositories.referentiel.ContractTypeRepository;
import com.api.cv.repositories.referentiel.OfferStatusRepository;
import com.api.cv.services.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class OfferMapperHelper {

    private final OfferStatusRepository offerStatusRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final UserService userService;

    public OfferMapperHelper(OfferStatusRepository offerStatusRepository,
                             ContractTypeRepository contractTypeRepository,
                             UserService userService) {
        this.offerStatusRepository = offerStatusRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.userService = userService;
    }

    public OfferStatus mapStatus(String statusCode) throws RessourceDbNotFoundException {
        return offerStatusRepository.findByCode(statusCode)
                .orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
    }

    public ContractType mapContractType(String contractTypeCode) throws RessourceDbNotFoundException {
        return contractTypeRepository.findByCode(contractTypeCode)
                .orElseThrow(() -> new RessourceDbNotFoundException(ErrorCode.A400));
    }

    public Offer setCreatedUser(Offer offer) throws ApiErrorException {
        offer.setCreatedUser(userService.getUserConnected());
        return offer;
    }
}
