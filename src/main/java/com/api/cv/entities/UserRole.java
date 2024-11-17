package com.api.cv.entities;

import java.io.Serial;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "api_rel_user_role")
public class UserRole extends BaseModel{

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5229300825996927212L;
	
	@OneToMany
	@Column(name = "user_id",nullable = false)
	private User user;
	 
	@OneToMany
	@Column(name = "role_id",nullable = false)
	private Role role;

}
