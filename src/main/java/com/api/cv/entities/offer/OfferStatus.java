package com.api.cv.entities.offer;


import java.io.Serial;

import org.hibernate.annotations.Where;

import com.api.cv.entities.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "api_offer_status")
@Where(clause = "is_delete = false")
public class OfferStatus extends BaseModel {
	
	@Serial
	private static final long serialVersionUID = -7898756852479386599L;
	
	@Column(name="libelle",nullable=false)
	private String libelle;
	
}
