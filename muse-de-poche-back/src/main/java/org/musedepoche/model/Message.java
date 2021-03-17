package org.musedepoche.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendingDate;
	
	@ManyToOne
	private Composer sender;
	
	@ManyToOne
	private Composition subject;
	
	private String text;
	
	public Message() {
		super();
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
