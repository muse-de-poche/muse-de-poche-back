package org.musedepoche.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Cette entité représente un message echangé entre co-collaborateurs.
 * 
 * @author Cyril R.
 * @see org.musedepoche.validator.MessageValidator
 * @since 0.1
 */
@Entity
public class Message {

	@Id
	@GeneratedValue
	@Column(updatable = false)
	@JsonView(IViews.IViewBasic.class)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(IViews.IViewDetail.class)
	private Date sendingDate;

	@ManyToOne
	@JoinColumn(updatable = false)
	@JsonView(IViews.IViewDetail.class)
	private Composer sender;

	@ManyToOne
	@JoinColumn(updatable = false)
	@JsonView(IViews.IViewDetail.class)
	private Composition subject;

	@Size(max = 400)
	@JsonView(IViews.IViewBasic.class)
	private String text;

	public Message() {
		super();
		this.sendingDate = new Date();
	}

	public Message(Long id, Date sendingDate, Composer sender, Composition subject, String text) {
		super();
		this.id = id;
		this.sendingDate = sendingDate;
		this.sender = sender;
		this.subject = subject;
		this.text = text;
	}

	public Message(Date sendingDate, Composer sender, Composition subject, String text) {
		super();
		this.sendingDate = sendingDate;
		this.sender = sender;
		this.subject = subject;
		this.text = text;
	}

	public Message(Composer sender, Composition subject, String text) {
		this(new Date(), sender, subject, text);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public Composer getSender() {
		return sender;
	}

	public void setSender(Composer sender) {
		this.sender = sender;
	}

	public Composition getSubject() {
		return subject;
	}

	public void setSubject(Composition subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
