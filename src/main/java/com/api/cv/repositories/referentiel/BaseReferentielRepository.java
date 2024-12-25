package com.api.cv.repositories.referentiel;

import com.api.cv.entities.referentiel.BaseModelReferentiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseReferentielRepository<E extends BaseModelReferentiel,T> extends JpaRepository<E, T> {

        Optional<E> findByUuid(String uuid);

        Optional<E> findByCode(String code);

}
