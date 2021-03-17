package org.musedepoche.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Lionel Rénier
 * Sound is a component which defines an audio source, its file path, and its position on its parent's track
 */

@Entity
@Table
public class Sound {

	@Id
	@GeneratedValue
	private Long id;

	private int position;

	private String file;

	@ManyToOne
	private Track track;

	public Sound() {
		super();
	}

	/**
	 * Sound constructor
	 * @param int position (position on the track)
	 * @param String file (path of the sound source)
	 * @param Track track (parent's Track) 
	 */
	public Sound(int position, String file, Track track) {
		super();
		this.position = position;
		this.file = file;
		this.track = track;
	}

	/**
	 * Sound constructor
	 * @param id
	 * @param int position (position on the track)
	 * @param String file (path of the sound source)
	 * @param Track track (parent's Track) 
	 */
	public Sound(Long id, int position, String file, Track track) {
		super();
		this.id = id;
		this.position = position;
		this.file = file;
		this.track = track;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	@Override
	public String toString() {
		return "Sound [id=" + id + ", position=" + position + ", file=" + file + ", track=" + track + "]";
	}

}
