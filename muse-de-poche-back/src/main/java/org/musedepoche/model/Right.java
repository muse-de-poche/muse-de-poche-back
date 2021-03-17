package org.musedepoche.model;

/**
 * Cette énumération représente les droits d'un co-compositeur sur les autres co-compositeur.
 * <ul>
 * <li>{@code FULL} : Le droit absolu sur toutes les pistes</li>
 * <li>{@code CREATE} : Droit de creation de nouvelles pistes</li>
 * <li>{@code WRITE} : Droit de mofification de toutes les pistes</li>
 * <li>{@code READONLY} : Ne peux que lire</li>
 * </ul>
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Right
 * @see org.musedepoche.model.Collaboration
 * @see org.musedepoche.model.Track
 */
public enum Right {
	FULL, CREATE, WRITE, READONLY
}
