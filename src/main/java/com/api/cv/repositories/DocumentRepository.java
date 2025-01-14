package com.api.cv.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.cv.entities.Document;
import com.api.cv.entities.User;

@Repository
public interface DocumentRepository extends BaseRepository<Document, Long> {
	
	
	Optional<Document> findByNomDocument(String nomDocument);
	
	List<Document> findByCreatedUser(User user);

	
	
}
