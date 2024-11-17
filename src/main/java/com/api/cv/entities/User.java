package com.api.cv.entities;

import java.io.Serial;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "api_user")
public class User extends BaseModel{
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -3995172752479386599L;

	@Column(name = "user_name",nullable = false,unique = true)
	private String userName;
	
	@Column(nullable = false,unique = true)
	private String email;
	
	@Column(name = "keycloak_id",nullable = false,unique = true)
	private String keycloakId;
	
	
}
