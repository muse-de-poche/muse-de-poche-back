package org.musedepoche.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Lionel Rénier Sound is a component which defines an audio source, its
 *         file path, and its position on its parent's track
 */

@Entity
@Table
public class Sound {

	@Id
	@GeneratedValue
	@JsonView(IViews.IViewTrack.class)
	private Long id;

	@JsonView(IViews.IViewTrack.class)
	private int position;

	@Lob
	@JsonView(IViews.IViewTrackDetail.class)
	private byte[] file;

	@JsonView(IViews.IViewTrack.class)
	private String name;

	@ManyToOne
	@JsonView(IViews.IViewTrackDetail.class)
	private Track track;

	public Sound() {
		super();
	}

	/**
	 * Sound constructor
	 * 
	 * @param int    position (position on the track)
	 * @param String file (path of the sound source)
	 * @param Track  track (parent's Track)
	 */
	public Sound(int position, byte[] file, Track track) {
		super();
		this.position = position;
		this.file = file;
		this.track = track;
	}

	/**
	 * Sound constructor
	 * 
	 * @param id
	 * @param int    position (position on the track)
	 * @param String file (path of the sound source)
	 * @param Track  track (parent's Track)
	 */
	public Sound(Long id, int position, byte[] file, Track track) {
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

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

}