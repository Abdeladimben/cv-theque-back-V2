package com.api.cv.entities.referentiel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "api_contract_type")
@SQLRestriction("is_delete = false")
public class ContractType extends BaseModelReferentiel {
	
	@Serial
	private static final long serialVersionUID = -7898756852479386599L;

}
