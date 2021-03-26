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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Composer {

	@Id
	@GeneratedValue
	@JsonView(IViews.IViewBasic.class)
	private Long id;
	
	@Column(length = 100)
	@JsonView(IViews.IViewBasic.class)
	private String pseudo;
	
	@Column(length = 100)
	@JsonView(IViews.IViewBasic.class)
	private String password;

	@Column(length = 50, unique = true)
	@JsonView(IViews.IViewBasic.class)
	private String lastname;

	@Column(length = 50)
	@JsonView(IViews.IViewBasic.class)
	private String firstname;

	@Column(length = 100)
	@JsonView(IViews.IViewBasic.class)
	private String country;

	@Email
	@Column(length = 255, unique = true)
	@JsonView(IViews.IViewBasic.class)
	private String email;

	@Temporal(TemporalType.DATE)
	@JsonView(IViews.IViewBasic.class)
	private Date subscribedDate;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	@JsonView(IViews.IViewComposerDetail.class)
	private List<Composition> compositions = new ArrayList<Composition>();
	
	@OneToMany(mappedBy = "composer", fetch = FetchType.LAZY)
	@JsonView(IViews.IViewComposerDetail.class)
	private List<Collaboration> collaborations = new ArrayList<Collaboration>();

	public Composer() {
		super();
		this.subscribedDate = new Date();
	}
	
	
	
	public Composer(String pseudo, String password) {
		super();
		this.pseudo = pseudo;
		this.password = password;
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

	public Composer(String pseudo, String password, String lastname, String firstname, String country,
			String email, Date subscribedDate) {
		super();
		this.pseudo = pseudo;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.country = country;
		this.email = email;
		this.subscribedDate = subscribedDate;
	}
	
	public Composer(Long id, String pseudo, String password, String lastname, String firstname, String country,
			String email) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.country = country;
		this.email = email;
	}



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
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



	@Override
	public String toString() {
		return "Composer [id=" + id + ", pseudo=" + pseudo + ", password=" + password + ", lastname=" + lastname
				+ ", firstname=" + firstname + ", country=" + country + ", email=" + email + ", subscribedDate="
				+ subscribedDate + ", compositions=" + compositions + ", collaborations=" + collaborations + "]";
	}

	

}
