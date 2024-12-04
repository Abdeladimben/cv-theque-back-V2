package com.api.cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.cv.entities.Offer;



@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

}
