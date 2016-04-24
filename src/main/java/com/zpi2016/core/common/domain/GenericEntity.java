package com.zpi2016.core.common.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Generic entity class.
 *
 * @author Filip Chrzescijanek
 *
 */

@MappedSuperclass
public class GenericEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@CreationTimestamp
	private Date createDate;

	@UpdateTimestamp
	private Date updateDate;

	@Version
	private Long version;

	public UUID getId() {
		return this.id;
	}

	/**
	 * Sets the ID of this entity using Hibernate's value generator
	 *
	 * @param id
	 *            ID to be assigned to the current entity
	 */
	public void setId(final UUID id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Sets the creation date of this entity using
	 * Hibernate's @CreationTimestamp annotation
	 *
	 * @param createDate
	 *            Creation date to be assigned to the current entity
	 */
	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Sets the update date of this entity using Hibernate's @UpdateTimestamp
	 * annotation
	 *
	 * @param updateDate
	 *            Update date to be assigned to the current entity
	 */
	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getVersion() {
		return this.version;
	}

	/**
	 * Sets the version of this entity using Hibernate's @Version annotation
	 *
	 * @param version
	 *            Version value to be assigned to the current entity
	 */
	public void setVersion(final Long version) {
		this.version = version;
	}

}
