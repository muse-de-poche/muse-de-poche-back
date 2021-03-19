package org.musedepoche.dao;

import java.util.List;

import org.musedepoche.model.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICompositionDao extends JpaRepository<Composition, Long> {

	//List<Composer> getAllComposersById(Long id);
	
	// findAllSorted
//	List<Composition> findSort(Sort sort);
	
	@Query("select c from Composition c where c.owner.id = :id")
	List<Composition> findByOwner(@Param("id") Long id);

	
	@Query("select c from Composition c left join fetch c.owner s where c.title like %:item% or s.pseudo like %:item%")
	List<Composition> findByTitleOrOwner(@Param("item") String item);
	
	/**
	 * Répond {@code true} si le compositeur {@code composer} est le {@link Composition#getOwner() propriètaire} de la {@code composition}.
	 * 
	 * @param composer
	 * @Param Composition 
	 * @return {@code true} si cmposer est le propriètaire de la composition, {@code false} sinon
	 * 
	 * @author Cyril R.
	 */
	@Query("select case when count(c) > 0 then true else false end from Composition c where c.id = :composition and c.owner.id = :composer")
	boolean isOwnerOf(@Param("composer") Long composer, @Param("composition") Long composition);
	
}
