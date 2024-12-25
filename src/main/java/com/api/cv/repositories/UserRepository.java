package com.api.cv.repositories;

import com.api.cv.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends BaseRepository<User, Long>{
	
	Optional<User> findByUserName(String userName);

}
