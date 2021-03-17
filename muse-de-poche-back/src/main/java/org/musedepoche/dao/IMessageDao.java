package org.musedepoche.dao;

import java.util.List;

import org.musedepoche.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

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
	List<Message> findBySender(Long id);
	
	/**
	 * Trouve tout les message d'une composition.
	 * 
	 * @param id clé primaire d'une composition
	 * @return la liste des messages trouvé, une liste vide sinon
	 */
	List<Message> findBySubject(Long id);
	
}
