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
	
	List<Composition> findByOwner(Long id);
	
//	List<Composition> findByTitleOrOwner(String title, String owner);
	
	@Query("select c from Composition c join fetch c.owner s where c.title like %:item% or s.firstname like %:owner%")
	List<Composition> findByTitleOrOwner(@Param("item") String item, @Param("owner") String owner);
	
}
