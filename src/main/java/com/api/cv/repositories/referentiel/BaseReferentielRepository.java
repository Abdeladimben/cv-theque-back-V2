package com.api.cv.repositories.referentiel;

import com.api.cv.entities.referentiel.BaseModelReferentiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseReferentielRepository<E extends BaseModelReferentiel,T> extends JpaRepository<E, T> {

        Optional<E> findByUuid(String uuid);

        Optional<E> findByCode(String code);

}
