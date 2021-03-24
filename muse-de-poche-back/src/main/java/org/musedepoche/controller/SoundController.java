package org.musedepoche.controller;

import java.util.Optional;

import org.musedepoche.dao.ISoundDao;
import org.musedepoche.dao.ITrackDao;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Sound;
import org.musedepoche.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/sound")
public class SoundController {

	@Autowired
	private ISoundDao soundDao;

	@Autowired
	private ITrackDao trackDao;

	@PostMapping("/{id}")
	@JsonView(IViews.IViewTrack.class)
	public Sound createSound(@RequestParam("file") MultipartFile multipartSound, @PathVariable Long id)
			throws Exception {
		Sound dbSound = new Sound();
		Optional<Track> getTrack = trackDao.findById(id);
		Track track = null;
		if (getTrack.isPresent()) {
			track = getTrack.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "track " + id + " doesn't exist");
		}

		dbSound.setName(track.getId() + "-" + track.getInstrument() + (track.getSounds().size() + 1));
		dbSound.setFile(multipartSound.getBytes());
		dbSound.setTrack(track);
		dbSound.setPosition(0);

		dbSound = soundDao.save(dbSound);

		return dbSound;
	}

	@GetMapping("/{id}")
	@JsonView(IViews.IViewTrack.class)
	public Sound findSound(@PathVariable Long id) {
		Sound sound = soundDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return sound;
	}
	
	@GetMapping(value="/file/{id}", produces= "audio/mpeg")
	public Resource findFileSound(@PathVariable Long id) {
		byte[] sound = soundDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getFile();

		return new ByteArrayResource(sound);
	}

	@PutMapping("/update/file/{id}")
	@JsonView(IViews.IViewTrack.class)
	public Sound updateSound(@RequestParam("file") MultipartFile multipartSound, @PathVariable Long id) throws Exception {
		if (!soundDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}

		Sound sound = soundDao.getOne(id);
		sound.setFile(multipartSound.getBytes());

		sound = soundDao.save(sound);
		return sound;
	}
	
	@PatchMapping("/update/data/{id}")
	@JsonView(IViews.IViewTrack.class)
	public Sound updateSoundData(@RequestBody Sound sound, @PathVariable Long id) throws Exception {
		if (!soundDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}

		Sound soundFetch = soundDao.getOne(id);
//		sound.setFile(multipartSound.getBytes());
		soundFetch.setName(sound.getName());
		soundFetch.setPosition(sound.getPosition());
		sound = soundDao.save(soundFetch);
		return sound;
	}

	@DeleteMapping("/{id}")
	public void deleteSound(@PathVariable Long id) {
		if (!soundDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}
		soundDao.deleteById(id);

		if (soundDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "resource not deleted");
		}
		return;
	}

}