package com.api.cv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.cv.entities.offer.ContractType;
import com.api.cv.entities.offer.OfferStatus;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
	   Optional<ContractType> findByCode(String code);
	
	
}
