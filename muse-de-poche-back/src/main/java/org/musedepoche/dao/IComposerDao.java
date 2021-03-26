package org.musedepoche.dao;

import java.util.Optional;
import org.musedepoche.model.Composer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IComposerDao extends JpaRepository<Composer, Long> {
	
	/**
	 * Trouve l'utilisateur avec toute c'est composition et collaboration
	 * 
	 * @param id
	 * @return un optional de composer
	 */
	@Query("select c from Composer c join fetch c.compositions co where c.id = :id")
	Optional<Composer> findByIdWithComposition(@Param("id") Long id);
	
	/**
	 * Trouve un utilisateur par rapport a son pseudo
	 * 
	 * @param pseudo
	 * @return un optional de composer
	 */
	@Query("select c from Composer c where c.pseudo = :pseudo")
	Optional<Composer> findByPseudo(@Param("pseudo") String pseudo);
	
	/**
	 * Trouve un utilisateur par rapport a son pseudo
	 * 
	 * @param pseudo
	 * @param password
	 * @return
	 */
	@Query("select c from Composer c where (c.pseudo = :login or c.email = :login) and c.password = :password")
	Optional<Composer> findByPseudoAndPassword(@Param("login") String login, @Param("password") String password);

}
