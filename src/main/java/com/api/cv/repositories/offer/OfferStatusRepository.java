package com.api.cv.repositories.offer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.cv.entities.offer.Offer;
import com.api.cv.entities.offer.OfferStatus;

@Repository
public interface OfferStatusRepository extends JpaRepository<OfferStatus, Long>{
    Optional<OfferStatus> findByCode(String code);
    

}
