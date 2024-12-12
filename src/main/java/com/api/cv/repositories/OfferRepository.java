package com.api.cv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.cv.entities.offer.Offer;




@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{
	 
	  void deleteByUuid(String uuid);
	    Optional<Offer> findByUuid(String uuid);
	  

}
