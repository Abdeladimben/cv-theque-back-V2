package com.api.cv.entities;

import org.hibernate.annotations.Where;

import com.api.cv.entities.referentiel.DocumentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "api_document")

@Where(clause = "is_delete = false")
public class Document extends BaseModel{

	@Column(name="nom_document")
	private String nomDocument;
	@Column(name="extension")
	private String extension;
	@Column(name="taille")
	private Long taille;
	
	@Column(name="data")
	private byte[ ] data;
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "created_user", nullable = false) 
    private User createdUser;
	
	@ManyToOne
	private DocumentType documentType;
}
