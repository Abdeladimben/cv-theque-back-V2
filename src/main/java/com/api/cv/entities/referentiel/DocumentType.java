package com.api.cv.entities.referentiel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="api_document_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentType extends BaseModelReferentiel {

	String name ;
	
}
