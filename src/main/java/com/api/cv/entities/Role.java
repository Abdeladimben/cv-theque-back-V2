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
@Table(name = "api_role")
public class Role extends BaseModel{
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 8009510240735873447L;
	
	@Column(name = "label",nullable = false,unique = true)
	private String label;
}
