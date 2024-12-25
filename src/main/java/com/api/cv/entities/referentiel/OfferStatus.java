package com.api.cv.entities.referentiel;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
	
}
