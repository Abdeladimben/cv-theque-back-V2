package com.api.cv.helpers;

import org.springframework.stereotype.Component;

import com.api.cv.entities.offer.ContractType;
import com.api.cv.entities.offer.OfferStatus;
import com.api.cv.repositories.ContractTypeRepository;
import com.api.cv.repositories.OfferStatusRepository;

@Component

public class StatusMapperHelper {

    private final OfferStatusRepository offerStatusRepository;
    private final ContractTypeRepository contractTypeRepository;

    public StatusMapperHelper(OfferStatusRepository offerStatusRepository,ContractTypeRepository contractTypeRepository) {
        this.offerStatusRepository = offerStatusRepository;
        this.contractTypeRepository=contractTypeRepository;
    }

    public OfferStatus mapStatus(String statusCode) {
        return offerStatusRepository.findByCode(statusCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid status code: " + statusCode));
    }
    
    public ContractType mapContractType(String contractTypeCode) {
        return contractTypeRepository.findByCode(contractTypeCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid status code: " + contractTypeCode));
    }
    
    
}
