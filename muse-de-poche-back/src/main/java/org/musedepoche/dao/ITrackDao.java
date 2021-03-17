package org.musedepoche.dao;

import org.musedepoche.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrackDao extends JpaRepository<Track, Long> {

}
