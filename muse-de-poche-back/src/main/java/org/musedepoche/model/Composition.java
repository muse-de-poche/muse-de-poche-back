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

import org.musedepoche.model.IViews.IViewBasic;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Lionel Rénier Composition is a main component of Muse de Poche, It
 *         defined a musical composition where one or many compositors can work
 *         together Contains one to many tracks 1 owner 1 metronome
 */

@Entity
@Table
public class Composition {
	@Id
	@GeneratedValue
	@JsonView(IViews.IViewBasic.class)
	private Long id;

	@JsonView(IViews.IViewBasic.class)
	private String title;

	@JsonView(IViews.IViewBasic.class)
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@JsonView(IViews.IViewBasic.class)
	@Temporal(TemporalType.DATE)
	private Date lastUpdate;

	@JsonView(IViews.IViewBasic.class)
	private int playsNumber;

	@JsonView(IViews.IViewDetail.class)
	@ManyToOne
	private Composer owner;

	@JsonView(IViews.IViewDetail.class)
	@OneToMany(mappedBy = "composition", fetch = FetchType.EAGER)
	private List<Collaboration> collaborations = new ArrayList<Collaboration>();

	@JsonView(IViews.IViewDetail.class)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "metronome")
	private Metronome metronome;

	@JsonView(IViews.IViewDetail.class)
	@OneToMany(mappedBy = "composition")
	private List<Track> tracks = new ArrayList<Track>();

	@JsonView(IViews.IViewBasic.class)
	private String blobPath;

	public Composition() {
		super();
		this.createdDate = new Date();
	}

	/**
	 * Composition constructor
	 * 
	 * @param id
	 * @param String      title (title of the composition)
	 * @param int         playsNumber (number of plays)
	 * @param Composition owner
	 * @param List        collaborations (List of collaborations)
	 */
	public Composition(Long id, String title, int playsNumber, Composer owner, List<Collaboration> collaborations) {
		super();
		this.id = id;
		this.title = title;
		this.playsNumber = playsNumber;
		this.owner = owner;
		this.collaborations = collaborations;
	}

	public Composition(String title, int playsNumber, Composer owner) {
		super();
		this.title = title;
		this.playsNumber = playsNumber;
		this.owner = owner;
	}

	/**
	 * 
	 * Composition constructor
	 * 
	 * @param String      title (title of the composition)
	 * @param int         playsNumber (number of plays)
	 * @param Composition owner
	 * @param List        collaborations (List of collaborations)
	 */
	public Composition(String title, int playsNumber, Composer owner, List<Collaboration> collaborations) {
		super();
		this.title = title;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getBlobPath() {
		return blobPath;
	}

	public void setBlobPath(String blobPath) {
		this.blobPath = blobPath;
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
