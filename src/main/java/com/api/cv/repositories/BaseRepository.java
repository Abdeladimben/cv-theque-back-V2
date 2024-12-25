package com.api.cv.repositories;

import com.api.cv.entities.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseRepository<E extends BaseModel,T> extends JpaRepository<E, T> {

    Optional<E> findByUuid(String uuid);

    Optional<E> findByCode(String code);
}
