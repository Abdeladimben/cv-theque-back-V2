package com.api.cv.entities.referentiel;


import java.io.Serial;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.annotations.Where;

import com.api.cv.entities.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "api_offer_status")
@Where(clause = "is_delete = false")
public class OfferStatus extends BaseModelReferentiel {
	
	@Serial
	private static final long serialVersionUID = -7898756852479386599L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	@Column
	private Long id;

	@Column(name = "uuid", nullable = true)
	private String uuid;

	@Column(name = "code", nullable = true)
	private String code;
	
}
