package org.musedepoche.dao;

import org.musedepoche.model.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompositionDao extends JpaRepository<Composition, Long> {

	//List<Composer> getAllComposersById(Long id);
	
}
