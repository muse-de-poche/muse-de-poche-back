package org.musedepoche.dao;

import java.util.List;

import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author Cyril R.
 * since 0.1
 */
public interface ICollaborationDao extends JpaRepository<Collaboration, Long> {
	
	/**
	 * Trouve toutes les collaborations d'une composition par sa clé primaire.
	 * 
	 * @param id clé primaire d'une composition
	 * @return la liste des collaborations trouvé, une liste vide sinon
	 */
	@Query("select c from Collaboration c where c.composition = :id")
	List<Collaboration> findByComposition(@Param("id") Long id);
	
	/**
	 * Trouve toutes les collaborations d'un compositeur par sa clé primaire.
	 * 
	 * @param id clé primaire d'un compositeur
	 * @return la liste des collaborations trouvé, une liste vide sinon
	 */
	@Query("select c from Collaboration c where c.composer = :id")
	List<Collaboration> findByComposer(@Param("id") Long id);
	
	/**
	 * Trouve toutes les collaborations d'un status en particulier.
	 * 
	 * @param status 
	 * @return la liste des collaborations trouvé, une liste vide sinon
	 */
	@Query("select c from Collaboration c where c.status = :status")
	List<Collaboration> findByStatus(@Param("status") Status status);
	
}
