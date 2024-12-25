package com.api.cv.mappers.referentiel;

import com.api.cv.dto.ReferentielDto;
import com.api.cv.entities.referentiel.OfferStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOfferStatusMapper extends IReferentielBaseMapper<OfferStatus, ReferentielDto>{
}
