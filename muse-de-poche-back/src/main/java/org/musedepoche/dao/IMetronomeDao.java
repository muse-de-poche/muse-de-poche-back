package org.musedepoche.dao;

import org.musedepoche.model.Metronome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMetronomeDao extends JpaRepository<Metronome, Long> {

}
