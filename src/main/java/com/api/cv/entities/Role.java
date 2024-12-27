package com.api.cv.entities;

import java.io.Serial;

import org.hibernate.annotations.Where;

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
@Table(name = "api_role")
@Where(clause = "is_delete = false")
public class Role extends BaseModel{
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 8009510240735873447L;
	
	@Column(name = "label",unique = true)
	private String label;

	@Column(name = "id_keycloak",nullable = false,unique = true)
	private String idKeycloak;

	@Column(name = "is_composite",columnDefinition = "boolean default false")
	private boolean composite;

	@Column(name = "is_client_role",columnDefinition = "boolean default false")
	private boolean clientRole;

	@Column(name = "id_container")
	private String containerId;
	
}
