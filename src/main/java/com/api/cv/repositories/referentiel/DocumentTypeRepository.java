package com.api.cv.repositories.referentiel;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.api.cv.entities.referentiel.DocumentType;

@Repository
public interface DocumentTypeRepository extends BaseReferentielRepository<DocumentType, Long> {

	
	Optional<DocumentType> getByCode(String code);
}
