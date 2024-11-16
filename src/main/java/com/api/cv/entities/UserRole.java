package com.api.cv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cv_rel_user_role")
public class UserRole {
	
	 @OneToMany
	@Column(name = "user_id",nullable = false,unique = true)
	private User user;
	 
	 @OneToMany
	@Column(name = "role_id",nullable = false,unique = true)
	private Role role;

}
