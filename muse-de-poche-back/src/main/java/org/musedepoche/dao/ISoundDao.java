package org.musedepoche.dao;

import org.musedepoche.model.Sound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISoundDao extends JpaRepository<Sound, Long> {

}
