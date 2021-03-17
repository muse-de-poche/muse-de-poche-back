package org.musedepoche.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Composer {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 50)
	private String lastname;

	@Column(length = 50)
	private String firstname;

	@Column(length = 100)
	private String country;

	@Column(length=255)
	private String email;

	@Temporal(TemporalType.DATE)
	private Date subscribedDate;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Composition> compositions = new ArrayList<Composition>();
	
	@OneToMany(mappedBy = "composer", fetch = FetchType.LAZY)
	private List<Collaboration> collaborations = new ArrayList<Collaboration>();

	public Composer() {
		super();
	}
	
	
	public Composer(String lastname, String firstname, String country, String email, Date subscribedDate,
			List<Composition> compositions, List<Collaboration> collaborations) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.country = country;
		this.email = email;
		this.subscribedDate = subscribedDate;
		this.compositions = compositions;
		this.collaborations = collaborations;
	}
	
	public Composer(String lastname, String firstname, String country, String email, Date subscribedDate) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.country = country;
		this.email = email;
		this.subscribedDate = subscribedDate;
	}

	public Long getId() {
		return id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getSubscribedDate() {
		return subscribedDate;
	}

	public void setSubscribedDate(Date subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

	public List<Composition> getCompositions() {
		return compositions;
	}

	public void setCompositions(List<Composition> compositions) {
		this.compositions = compositions;
	}
	

	public List<Collaboration> getCollaborations() {
		return collaborations;
	}


	public void setCollaborations(List<Collaboration> collaborations) {
		this.collaborations = collaborations;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Composer [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", country=" + country
				+ ", email=" + email + ", subscribedDate=" + subscribedDate + ", compositions=" + compositions + "]";
	}

}
