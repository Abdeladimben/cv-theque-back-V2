package com.api.cv.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.api.cv.entities.offer.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{
	 
	  void deleteByUuid(String uuid);
	    Optional<Offer> findByUuid(String uuid);
	  
	    
	    //pagination
	    
	    
	    @Query("SELECT o FROM Offer o WHERE " +
	            "(:title IS NULL OR o.title LIKE %:title%) AND " +
	            "(:ville IS NULL OR o.ville LIKE %:ville%) AND " +
	            "(:remuneration IS NULL OR o.remuneration >= :remuneration) AND " +
	            "(:dureeContrat IS NULL OR o.dureeContrat = :dureeContrat)")
	    Page<Offer> findFilteredOffers(
	            @Param("title") String title,
	            @Param("ville") String ville,
	            @Param("remuneration") Double remuneration,
	            @Param("dureeContrat") Integer dureeContrat,
	            Pageable pageable
	    );

}
