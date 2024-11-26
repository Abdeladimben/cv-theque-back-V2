package com.api.cv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.entities.User;

import lombok.RequiredArgsConstructor;

@Repository

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	
	
	Optional<User> findByUserName(String userName);
	
	

}
