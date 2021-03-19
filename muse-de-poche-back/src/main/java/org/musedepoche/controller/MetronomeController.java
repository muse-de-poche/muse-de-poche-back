package org.musedepoche.controller;

import java.util.Optional;

import org.musedepoche.dao.ICompositionDao;
import org.musedepoche.dao.IMetronomeDao;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Metronome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/metronome")
public class MetronomeController {

	@Autowired
	private IMetronomeDao metronomeDao;

	@Autowired
	private ICompositionDao compositionDao;

	@GetMapping("/{id}")
	public Metronome findMetronomeById(@PathVariable Long id) {
		Optional<Metronome> metronome = metronomeDao.findById(id);

		if (metronome.isPresent()) {
			return metronome.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/composition/{id}")
	@JsonView(IViews.IViewCompositionDetail.class)
	public Metronome findByComposition(@PathVariable Long id) {
		Optional<Composition> composition = compositionDao.findById(id);
		Composition compo;

		if (composition.isPresent()) {
			compo = composition.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find composition");
		}

		if (compo.getMetronome() != null) {
			return compo.getMetronome();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find metronome");
		}
	}

	@PostMapping("")
	@JsonView(IViews.IViewCompositionDetail.class)
	public Metronome create(@RequestBody Metronome metronome) {
		metronome = metronomeDao.save(metronome);
		return metronome;
	}

	@PutMapping("/{id}")
	@JsonView(IViews.IViewCompositionDetail.class)
	public Metronome update(@RequestBody Metronome metronome, @PathVariable Long id) {
		if (!metronomeDao.existsById(id) || !id.equals(metronome.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}

		metronome = metronomeDao.save(metronome);
		return metronome;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!metronomeDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}
		metronomeDao.deleteById(id);

		if (metronomeDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "resource not deleted");
		}
		return;
	}
}
