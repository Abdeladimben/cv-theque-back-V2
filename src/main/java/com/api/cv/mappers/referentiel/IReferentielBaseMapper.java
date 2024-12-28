package com.api.cv.mappers.referentiel;

import com.api.cv.entities.referentiel.BaseModelReferentiel;

import java.util.List;

public interface IReferentielBaseMapper<E extends BaseModelReferentiel,D> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);

}
