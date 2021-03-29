package org.musedepoche.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Lionel Rénier 
 * Track is a container component, it contains one or many audio sources
 * it belongs to 1 composition
 *
 */

@Entity
@Table
public class Track {

	@Id
	@GeneratedValue
	@JsonView(IViews.IViewBasic.class)
	private Long id;

	@JsonView(IViews.IViewBasic.class)
	private int duration;

	@JsonView(IViews.IViewBasic.class)
	private String instrument;

	@ManyToOne
	@JsonView(IViews.IViewWithComposition.class)
	private Composition composition;

	@OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
	@JsonView(IViews.IViewDetail.class)
	private List<Sound> sounds = new ArrayList<Sound>();

	public Track() {
		super();
	}

	/**
	 * Track Constructor 
	 * @param int duration (duration/length of the track)
	 * @param String instrument (instrument played on that track)
	 * @param List sounds (list of sounds contains in that track)
	 * @param Composition composition (parent's composition)
	 */
	public Track(int duration, String instrument, List<Sound> sounds, Composition composition) {
		super();
		this.duration = duration;
		this.instrument = instrument;
		this.sounds = sounds;
		this.composition = composition;
	}

	/**
	 * Track Constructor 
	 * @param id
	 * @param int duration (duration/length of the track)
	 * @param String instrument (instrument played on that track)
	 * @param List sounds (list of sounds contains in that track)
	 * @param Composition composition (parent's composition)
	 */
	public Track(Long id, int duration, String instrument, List<Sound> sounds, Composition composition) {
		super();
		this.id = id;
		this.duration = duration;
		this.instrument = instrument;
		this.sounds = sounds;
		this.composition = composition;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public List<Sound> getSounds() {
		return sounds;
	}

	public void setSounds(List<Sound> sounds) {
		this.sounds = sounds;
	}

	public boolean add(Sound e) {
		return sounds.add(e);
	}

	public boolean addAll(Collection<? extends Sound> c) {
		return sounds.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Sound> c) {
		return sounds.addAll(index, c);
	}

	public void add(int index, Sound element) {
		sounds.add(index, element);
	}

	public Sound remove(int index) {
		return sounds.remove(index);
	}

	public Composition getComposition() {
		return composition;
	}

	public void setComposition(Composition composition) {
		this.composition = composition;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", duration=" + duration + ", instrument=" + instrument + ", composition="
				+ composition + ", sounds=" + sounds + "]";
	}

}
