package org.musedepoche.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Lionel Rénier
 * Composition is a main component of Muse de Poche,
 * It defined a musical composition where one or many compositors can work together
 * Contains one to many tracks
 * 1 owner
 * 1 metronome
 */

@Entity
@Table
public class Composition {
	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	private int playsNumber;

	@ManyToOne
	private Composer owner;

	@OneToMany(mappedBy = "composition", fetch = FetchType.EAGER)
	private List<Collaboration> collaborations = new ArrayList<Collaboration>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "metronome")
	private Metronome metronome;

	@OneToMany(mappedBy = "composition", fetch = FetchType.LAZY)
	private List<Track> tracks = new ArrayList<Track>();

	public Composition() {
		super();
	}

	/**
	 * Composition constructor
	 * @param id
	 * @param Date createdDate (date of creation of the composition) 
	 * @param int playsNumber (number of plays)
	 * @param Composition owner
	 * @param List collaborations (List of collaborations)
	 */
	public Composition(Long id, Date createdDate, int playsNumber, Composer owner, List<Collaboration> collaborations) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.playsNumber = playsNumber;
		this.owner = owner;
		this.collaborations = collaborations;
	}
	
	public Composition(Date createdDate, int playsNumber, Composer owner) {
		super();
		this.createdDate = createdDate;
		this.playsNumber = playsNumber;
		this.owner = owner;
	}
  	/**
	 * 
	 * Composition constructor
	 * @param Date createdDate (date of creation of the composition) 
	 * @param int playsNumber (number of plays)
	 * @param Composition owner
	 * @param List collaborations (List of collaborations)
	 */
	public Composition(Date createdDate, int playsNumber, Composer owner, List<Collaboration> collaborations) {
		super();
		this.createdDate = createdDate;
		this.playsNumber = playsNumber;
		this.owner = owner;
		this.collaborations = collaborations;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getPlaysNumber() {
		return playsNumber;
	}

	public void setPlaysNumber(int playsNumber) {
		this.playsNumber = playsNumber;
	}

	public Composer getOwner() {
		return owner;
	}
	
	public void setOwner(Composer owner) {
		this.owner = owner;
	}

	public List<Collaboration> getCollaborations() {
		return collaborations;
	}

	public void setCollaborations(List<Collaboration> collaborations) {
		this.collaborations = collaborations;
	}

	public boolean addCollaboration(Collaboration e) {
		return collaborations.add(e);
	}

	public Collaboration removeCollaboration(int index) {
		return collaborations.remove(index);
	}

	public Metronome getMetronome() {
		return metronome;
	}

	public void setMetronome(Metronome metronome) {
		this.metronome = metronome;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public boolean add(Track e) {
		return tracks.add(e);
	}

	public void add(int index, Track element) {
		tracks.add(index, element);
	}

	public Track remove(int index) {
		return tracks.remove(index);
	}

}
