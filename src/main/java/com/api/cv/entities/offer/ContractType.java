package com.api.cv.entities.offer;

import java.io.Serial;

import com.api.cv.entities.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ContractType extends BaseModel {
	
	@Serial
	private static final long serialVersionUID = -7898756852479386599L;
	@Column(name="libelle",nullable=false)
	private String libelle;

}
