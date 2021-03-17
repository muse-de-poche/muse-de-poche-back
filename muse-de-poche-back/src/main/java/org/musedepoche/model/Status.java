package org.musedepoche.model;

/**
 * Cette énumération représente les différent status d'une collaboration.
 * <ul>
 * <li>{@code WAITING} : Demande en cours attente de traitement.</li>
 * <li>{@code ACCEPTED} : Demande accepté par le propriétaire de la composition</li>
 * <li>{@code REJECTED} : Demande rejeté par le propriétaire de la composition</li>
 * <li>{@code CANCELED} : Demande annulé par le demandeur</li>
 * <li>{@code REVOKED} : Le demandeur a mis fin à sa collaboration</li>
 * <li>{@code BANNED} : Le propriétaire de la composition a banni la collaboration</li>
 * </ul>
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Collaboration
 * @see org.musedepoche.model.Composition
 */
public enum Status {
	WAITING, ACCEPTED, REJECTED, CANCELED, REVOKED, BANNED
}
