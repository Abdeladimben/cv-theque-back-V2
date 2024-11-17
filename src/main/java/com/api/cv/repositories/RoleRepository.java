package com.api.cv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.cv.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
