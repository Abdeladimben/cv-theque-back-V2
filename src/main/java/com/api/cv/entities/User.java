package com.api.cv.entities;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	
	@Column(name = "keycloak_id",nullable = true,unique = true)
	private String keycloakId;
	
	@Transient
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private  List<UserRole> userRoles= new ArrayList<>();;
	
	
	public List<Role> getRoles() {
        return userRoles.stream()
        		.map(UserRole::getRole)
        		.collect(Collectors.toList())
        		;
    }
	
}
