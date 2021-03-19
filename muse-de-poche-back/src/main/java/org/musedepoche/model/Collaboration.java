package org.musedepoche.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

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
	@Column(updatable = false)
	@JsonView(IViews.IViewBasic.class)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	@JsonView(IViews.IViewBasic.class)
	private Date demandDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(IViews.IViewDetail.class)
	private Date validatedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(IViews.IViewDetail.class)
	private Date lastUpdate;

	@ManyToOne
	@JoinColumn(updatable = false)
	@JsonView(IViews.IViewCollaborationComposition.class)
	private Composition composition;

	@ManyToOne
	@JoinColumn(updatable = false)
	@JsonView(IViews.IViewCollaborationComposer.class)
	private Composer composer;

	@Enumerated(EnumType.STRING)
	@JsonView(IViews.IViewBasic.class)
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(name = "rights")
	@JsonView(IViews.IViewBasic.class)
	private Right right;
	
	@Column(updatable = false)
	@Size(max = 400)
	@JsonView(IViews.IViewBasic.class)
	private String text;

	public Collaboration() {
		this.demandDate = new Date();
		this.status = Status.WAITING;
		this.right = Right.READONLY;
	}

	/**
	 * 
	 * @param id
	 * @param demandDate
	 * @param validatedDate
	 * @param lastUpdate
	 * @param composition
	 * @param composer
	 * @param status
	 * @param right
	 * @param text
	 */
	public Collaboration(Long id, Date demandDate, Date validatedDate, Date lastUpdate, Composition composition,
			Composer composer, Status status, Right right, String text) {
		super();
		this.id = id;
		this.demandDate = demandDate;
		this.validatedDate = validatedDate;
		this.lastUpdate = lastUpdate;
		this.composition = composition;
		this.composer = composer;
		this.status = status;
		this.right = right;
		this.text = text;
	}
	
	/**
	 * 
	 * @param demandDate
	 * @param validatedDate
	 * @param lastUpdate
	 * @param composition
	 * @param composer
	 * @param status
	 * @param right
	 * @param text
	 */
	public Collaboration(Date demandDate, Date validatedDate, Date lastUpdate, Composition composition,
			Composer composer, Status status, Right right, String text) {
		super();
		this.demandDate = demandDate;
		this.validatedDate = validatedDate;
		this.lastUpdate = lastUpdate;
		this.composition = composition;
		this.composer = composer;
		this.status = status;
		this.right = right;
		this.text = text;
	}

	/**
	 * Constructeur minimal. Ajoute des valeurs par defaut.
	 * <ul>
	 * 	<li>{@code demandDate} : La date et heure du jour</li>
	 * 	<li>{@code validatedDate} : {@code null}</li>
	 * 	<li>{@code lastUpdate} : {@code null}</li>
	 * 	<li>{@code status} : {@link Status#WAITING}</li>
	 * 	<li>{@code right} : {@link Right#READONLY}</li>
	 * </ul>
	 * 
	 * @param composition
	 * @param composer
	 * @param text
	 */
	public Collaboration(Composition composition, Composer composer, String text) {
		this(new Date(), null, null, composition, composer, Status.WAITING, Right.READONLY, text);
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
