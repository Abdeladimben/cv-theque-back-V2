package com.api.cv.entities.referentiel;

import com.api.cv.helpers.Utils;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Data
@MappedSuperclass
public abstract class BaseModelReferentiel implements Serializable{
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	@Column
	private Long id;
	
    @Column(name = "uuid", nullable = true)
    private String uuid;

    @Column(name = "code", nullable = true)
    private String code;

    @Column(name = "description", nullable = true)
    private String description;

	@Column(name="libelle")
	private String libelle;

    @Column(name = "is_delete", nullable = true,columnDefinition = "boolean default false")
    private boolean isDelete;

    @Column(name = "is_statut", nullable = true,columnDefinition = "boolean default true")
    private boolean isStatut;
    
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

	@PrePersist
	public void prePersist() {
		this.isDelete = false;
		this.isStatut = true;
	}

	@PreUpdate
	public void preUpdate() {

	}

	@PreRemove
	public void preRemove() {

	}

	@PreDestroy
	public void preDestroy() {

	}

	@PostPersist
	public void postPersist() {
		if (this.getId() != null) { // Ensure the ID is generated
			String uuid = Utils.getHashedUuid(this.createDateTime.toInstant(ZoneOffset.UTC), this.getId());
			this.setUuid(uuid); // Set the UUID
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModelReferentiel other = (BaseModelReferentiel) obj;
		return Objects.equals(id, other.id) && Objects.equals(uuid, other.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uuid);
	}
	
	

}
