package com.zpi2016.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Generic entity class.
 *
 * @author Filip Chrzescijanek
 *
 */

@MappedSuperclass
public class GenericEntity<T extends GenericEntity<T>> {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "ID")
	private Integer id;

	@CreationTimestamp
	@Column(name = "CREATE_DATE")
	private Date createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Long version;

	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets the ID of this entity using Hibernate's value generator
	 *
	 * @param id
	 *            ID to be assigned to the current entity
	 */
	public void setId(final Integer id) {
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
