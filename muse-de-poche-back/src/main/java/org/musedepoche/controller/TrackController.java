package org.musedepoche.controller;

import java.util.List;
import java.util.Optional;

import org.musedepoche.dao.ICompositionDao;
import org.musedepoche.dao.ITrackDao;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/track")
public class TrackController {

	@Autowired
	private ITrackDao trackDao;
	
	@Autowired
	private ICompositionDao compositionDao;
	
	@GetMapping("/{id}")
	@JsonView(IViews.IViewTrack.class)
	public Track findTrackById(@PathVariable Long id) {
		Optional<Track> track = trackDao.findById(id);

		if (track.isPresent()) {
			return track.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/composition/{id}")
	@JsonView(IViews.IViewCompositionDetail.class)
	public List<Track> findByComposition(@PathVariable Long id) {
		Optional<Composition> composition = compositionDao.findById(id);
		Composition compo;

		if (composition.isPresent()) {
			compo = composition.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find composition");
		}

		if (compo.getTracks() != null) {
			return compo.getTracks();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find track");
		}
	}

	@PostMapping("")
	@JsonView(IViews.IViewTrack.class)
	public Track create(@RequestBody Track track) {
		track = trackDao.save(track);
		return track;
	}

	@PutMapping("/{id}")
	@JsonView(IViews.IViewTrack.class)
	public Track update(@RequestBody Track track, @PathVariable Long id) {
		if (!trackDao.existsById(id) || !id.equals(track.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}

		track = trackDao.save(track);
		return track;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!trackDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}
		trackDao.deleteById(id);

		if (trackDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "resource not deleted");
		}
		return;
	}
}
