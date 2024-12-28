package com.api.cv.repositories;

import com.api.cv.entities.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends BaseModel,T> extends JpaRepository<E, T> , JpaSpecificationExecutor<E>{

    Optional<E> findByUuid(String uuid);

    Optional<E> findByCode(String code);
}
