package org.musedepoche.dao;

import java.util.List;

import org.musedepoche.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Message
 * @see Character#isWhitespace(int)
 * @since 0.1
 */
public interface IMessageDao extends JpaRepository<Message, Long> {
	
	/**
	 * Trouve tout les message d'un compositeur.
	 * 
	 * @param id clé primaire d'un compositeur
	 * @return la liste des messages trouvé, une liste vide sinon
	 */
	@Query("select m from Message m where m.sender.id = :id")
	List<Message> findBySender(@Param("id") Long id);
	
	/**
	 * Trouve tout les message d'une composition.
	 * 
	 * @param id clé primaire d'une composition
	 * @return la liste des messages trouvé, une liste vide sinon
	 */
	@Query("select m from Message m where m.subject.id = :id")
	List<Message> findBySubject(Long id);
	
}
