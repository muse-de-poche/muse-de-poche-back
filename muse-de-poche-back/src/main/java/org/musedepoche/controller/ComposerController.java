package org.musedepoche.controller;

import java.util.List;
import java.util.Optional;

import org.musedepoche.dao.IComposerDao;
import org.musedepoche.model.Composer;
import org.musedepoche.model.IViews;
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
@RequestMapping("/composer")
public class ComposerController {
	
	@Autowired
	private IComposerDao composerDao;
	
	@GetMapping("")
	@JsonView(IViews.IViewComposer.class)
	public List<Composer> list() {
		List<Composer> composer = composerDao.findAll();

		return composer;
	}
	
	@PostMapping("")
	@JsonView(IViews.IViewComposer.class)
	public Composer createAccount(@RequestBody Composer composer) {
		composer = composerDao.save(composer);

		return composer;
	}
	
	@GetMapping("/{id}/detail")
	@JsonView(IViews.IViewComposerDetail.class)
	public Composer detail(@PathVariable Long id) {
		Optional<Composer> optComposer = composerDao.findByIdWithCompositionAndCollaboration(id);

		if (optComposer.isPresent()) {
			return optComposer.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@GetMapping("/{id}")
	@JsonView(IViews.IViewComposer.class)
	public Composer composer(@PathVariable Long id) {
		Optional<Composer> optComposer = composerDao.findById(id);

		if (optComposer.isPresent()) {
			return optComposer.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@GetMapping("/{pseudo}/pseudo")
	@JsonView(IViews.IViewComposer.class)
	public Composer findPseudo(@PathVariable String pseudo) {
		Optional<Composer> optComposer = composerDao.findByPseudo(pseudo);

		if (optComposer.isPresent()) {
			return optComposer.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	@PutMapping("/{id}")
	@JsonView(IViews.IViewComposer.class)
	public Composer update(@RequestBody Composer composer, @PathVariable Long id) {
		if (!composerDao.existsById(id) || !id.equals(composer.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}

		composer = composerDao.save(composer);

		return composer;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!composerDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}

		composerDao.deleteById(id);

		if (composerDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Unable to find resource");
		}
	}
	
	@PostMapping("/login")
	@JsonView(IViews.IViewComposer.class)
	public Composer findByPseudoAndPassword(@RequestBody Composer composer) {
		Optional<Composer> optComposer = composerDao.findByPseudoAndPassword(composer.getPseudo(),composer.getPassword());
		
		if (optComposer.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");	
		}else {
			return optComposer.get();
		}
	}

}
