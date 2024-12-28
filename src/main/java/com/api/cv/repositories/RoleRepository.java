package com.api.cv.repositories;

import com.api.cv.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long>{

    Optional<Role> findByLabel(String Label);

}
