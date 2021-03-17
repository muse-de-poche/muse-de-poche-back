package org.musedepoche.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cette entité représente la relation entre une composition et un compositeur.
 * En pratique ceci représente un co-compositeur d'une composition. 
 * Car un compositeur n'a pas à s'inviter sur sa propre composition, cela n'aurait pas de sens.
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Status
 * @see org.musedepoche.model.Right
 * @see org.musedepoche.model.Composer
 * @see org.musedepoche.model.Composition
 * @since 0.1
 */
@Entity
public class Collaboration {
	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date demandDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date validatedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@ManyToOne
	private Composition composition;

	@ManyToOne
	private Composer composer;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(name = "rights")
	private Right right;

	public Collaboration() {

	}

	public Collaboration(Date demandDate, Date validatedDate, Date lastUpdate, Composition composition,
			Composer composer, Status status, Right right) {
		super();
		this.demandDate = demandDate;
		this.validatedDate = validatedDate;
		this.lastUpdate = lastUpdate;
		this.composition = composition;
		this.composer = composer;
		this.status = status;
		this.right = right;
	}

	public Collaboration(Long id, Date demandDate, Date validatedDate, Date lastUpdate, Composition composition,
			Composer composer, Status status, Right right) {
		super();
		this.id = id;
		this.demandDate = demandDate;
		this.validatedDate = validatedDate;
		this.lastUpdate = lastUpdate;
		this.composition = composition;
		this.composer = composer;
		this.status = status;
		this.right = right;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}

	public Date getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(Date validatedDate) {
		this.validatedDate = validatedDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Composition getComposition() {
		return composition;
	}

	public void setComposition(Composition composition) {
		this.composition = composition;
	}

	public Composer getComposer() {
		return composer;
	}

	public void setComposer(Composer composer) {
		this.composer = composer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "Collaboration [id=" + id + ", demandDate=" + demandDate + ", validatedDate=" + validatedDate
				+ ", lastUpdate=" + lastUpdate + ", composition=" + composition + ", composer=" + composer
				+ ", status=" + status + ", right=" + right + "]";
	}

}
