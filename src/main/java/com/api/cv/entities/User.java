package com.api.cv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cv_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name = "user_name",nullable = false,unique = true)
	private String userName;
	
	@Column(nullable = false,unique = true)
	private String email;
	
	@Column(name = "keycloak_id",nullable = false,unique = true)
	private String keycloakId;
	
}
