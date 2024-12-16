package com.api.cv.entities.offer;

import java.io.Serial;

import org.hibernate.annotations.Where;

import com.api.cv.entities.BaseModel;
import com.api.cv.entities.User;

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
@Where(clause = "is_delete = false")
public class Offer extends BaseModel{
	
	@Serial
	private static final long serialVersionUID = -7898756852479386599L;

	@Column(name = "title",nullable = false)
	private String title;

	@Column(name="post")
	private String post;
	
	@Column(name="ville")
	private String ville;
	
	@Column(name="remuneration")
	private double remuneration;
	
	@Column(name="duree_contrat")
	private int dureeContrat;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "created_user", nullable = false) 
    private User createdUser;
	
	@ManyToOne
	@JoinColumn(name = "offer_status", nullable = false)
	private OfferStatus offerStatus;
	
	@ManyToOne
	@JoinColumn(name = "contract_type", nullable = false)
	private ContractType contractType;
	
}
