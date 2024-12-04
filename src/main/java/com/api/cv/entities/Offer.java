package com.api.cv.entities;

import java.io.Serial;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "api_offer")
public class Offer extends BaseModel{
	
	@Serial
	private static final long serialVersionUID = -7898756852479386599L;

	@Column(name = "title",nullable = false)
	private String title;
	@Column(name = "description",nullable = false)
	private String description;
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "created_user", nullable = false) 
    private User createdUser;
    
  
	
	@Column(name = "status",nullable = false)
	private String status;
	
}
