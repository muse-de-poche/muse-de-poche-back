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
	
}
