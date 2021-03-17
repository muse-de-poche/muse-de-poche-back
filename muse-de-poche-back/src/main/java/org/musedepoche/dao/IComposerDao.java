package org.musedepoche.dao;

import org.musedepoche.model.Composer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComposerDao extends JpaRepository<Composer, Long> {

}
