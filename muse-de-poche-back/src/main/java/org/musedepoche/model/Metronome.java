package org.musedepoche.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Lionel Rénier
 * Metronome is a unique component defined in its parent's composition
 * it contains a metric, a tempo, and a click type
 */

@Entity
@Table
public class Metronome {

	@Id
	@GeneratedValue
	private Long id;

	private String metric;

	private int clickType;

	private int bpm;

	public Metronome() {
		super();
	}

	/**
	 * Metronome constructor
	 * @param String metric (metric of the metronome)
	 * @param int clickType (the click type identicator)
	 * @param int bpm (beats per minut speed)
	 */
	public Metronome(String metric, int clickType, int bpm) {
		super();
		this.metric = metric;
		this.clickType = clickType;
		this.bpm = bpm;
	}

	/**
	 * Metronome constructor
	 * @param id
	 * @param String metric (metric of the metronome)
	 * @param int clickType (the click type identicator)
	 * @param int bpm (beats per minut speed)
	 */
	public Metronome(Long id, String metric, int clickType, int bpm) {
		super();
		this.id = id;
		this.metric = metric;
		this.clickType = clickType;
		this.bpm = bpm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public int getClickType() {
		return clickType;
	}

	public void setClickType(int clickType) {
		this.clickType = clickType;
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	@Override
	public String toString() {
		return "Metronome [id=" + id + ", metric=" + metric + ", clickType=" + clickType + ", bpm=" + bpm + "]";
	}

}
