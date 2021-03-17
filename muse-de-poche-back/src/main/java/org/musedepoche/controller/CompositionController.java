package org.musedepoche.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.musedepoche.dao.ICompositionDao;
import org.musedepoche.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
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

@RestController
@RequestMapping("/composition")
public class CompositionController {

	@Autowired
	private ICompositionDao compositionDao;

	@GetMapping("")
	public List<Composition> getAllCompositions() {
		return compositionDao.findAll();
	}

	@GetMapping("/composer/{id}")
	public List<Composition> getAllCompositionsByComposer(@PathVariable Long id) {
		List<Composition> listComposer = compositionDao.findByOwner(id);
		return listComposer;
	}

	@GetMapping("/{id}")
	public Composition findOneByComposition(@PathVariable Long id) {
		Optional<Composition> compo = compositionDao.findById(id);

		if (compo.isPresent()) {
			return compo.get();
		}

		return null;
	}
	
	@GetMapping("/search/{item}")
	public List<Composition> search(@PathVariable String item) {
		List<Composition> compos = compositionDao.findByTitleOrOwner(item, item);
		

		return compos;
	}

	@PostMapping("")
	public Composition create(@RequestBody Composition compo) {
		compo.setCreatedDate(new Date());
		compo = compositionDao.save(compo);
		return compo;
	}

	@PutMapping("/{id}")
	public Composition update(@RequestBody Composition compo, @PathVariable Long id) {
		if (!compositionDao.existsById(id) || !id.equals(compo.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}
		compo.setLastUpdate(new Date());
		compo = compositionDao.save(compo);
		return compo;
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!compositionDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
		}

		compositionDao.deleteById(id);

		if (compositionDao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "resource not deleted");
		}
		return;
	}
	
	@GetMapping("/plays-number")
	public List<Composition> sortedByPlaysNumber() {
		Page<Composition> compos = compositionDao.findAll(
				  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "playsNumber")));
		return compos.toList();
	}
	
	@GetMapping("/created-date")
	public List<Composition> sortedByCreatedDate() {
		Page<Composition> compos = compositionDao.findAll(
				  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate")));
		return compos.toList();
	}
	
	@GetMapping("/update-date")
	public List<Composition> sortedByUpdateDate() {
		Page<Composition> compos = compositionDao.findAll(
				  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updateDate")));
		return compos.toList();
	}
}
