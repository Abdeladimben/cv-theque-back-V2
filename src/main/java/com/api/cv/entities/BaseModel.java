package com.api.cv.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.cv.helpers.Utils;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@MappedSuperclass
public abstract class BaseModel implements Serializable{
	
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
		String Uuid = Utils.getHashedUuid(this.createDateTime, this.getId());
		this.setUuid(Uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModel other = (BaseModel) obj;
		return Objects.equals(id, other.id) && Objects.equals(uuid, other.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uuid);
	}
	
	

}
