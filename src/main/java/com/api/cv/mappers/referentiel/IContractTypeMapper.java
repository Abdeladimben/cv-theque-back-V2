package com.api.cv.mappers.referentiel;

import com.api.cv.dto.ReferentielDto;
import com.api.cv.entities.referentiel.ContractType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IContractTypeMapper extends IReferentielBaseMapper<ContractType, ReferentielDto>{
}
