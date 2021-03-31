package org.musedepoche.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.musedepoche.dao.ICompositionDao;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/composition")
public class CompositionController {

	@Autowired
	private ICompositionDao compositionDao;

	@JsonView(IViews.IViewCompositionDetail.class)
	@GetMapping("")
	public List<Composition> getAllCompositions() {
		return compositionDao.findAll();
	}

	/**
	 * Find all compositions of a owner
	 * @param Long id - id of the owner
	 * @return List of Compositions
	 */
	@GetMapping("/composer/{id}")
	@JsonView(IViews.IViewCompositionDetail.class)
	public List<Composition> getAllCompositionsByComposer(@PathVariable Long id) {
		List<Composition> listComposer = compositionDao.findByOwner(id);
		return listComposer;
	}

	/**
	 * find a compositon by its id
	 * @param Long id - id of the composition
	 * @return Composition/null
	 */
	@GetMapping("/{id}")
	@JsonView(IViews.IViewCompositionDetail.class)
	public Composition findOneByComposition(@PathVariable Long id) {
		Optional<Composition> compo = compositionDao.findById(id);

		if (compo.isPresent()) {
			return compo.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
		}
	}
	
	/**
	 * search from composition title or owner pseudo
	 * @param String item - query
	 * @return List of Compositions
	 */
	@GetMapping("/search/{item}")
	@JsonView(IViews.IViewCompositionDetail.class)
	public List<Composition> search(@PathVariable String item) {
		List<Composition> compos = compositionDao.findByTitleOrOwner(item);
		

		return compos;
	}

	@PostMapping("")
	@JsonView(IViews.IViewCompositionDetail.class)
	public Composition create(@RequestBody Composition compo) {
		compo = compositionDao.save(compo);
		return compo;
	}

	@PutMapping("/{id}")
	@JsonView(IViews.IViewCompositionDetail.class)
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
	@JsonView(IViews.IViewCompositionDetail.class)
	public List<Composition> sortedByPlaysNumber() {
		Page<Composition> compos = compositionDao.findAll(
				  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "playsNumber")));
		return compos.toList();
	}
	
	@GetMapping("/created-date")
	@JsonView(IViews.IViewCompositionDetail.class)
	public List<Composition> sortedByCreatedDate() {
		Page<Composition> compos = compositionDao.findAll(
				  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate")));
		return compos.toList();
	}
	
	@GetMapping("/update-date")
	@JsonView(IViews.IViewCompositionDetail.class)
	public List<Composition> sortedByUpdateDate() {
		Page<Composition> compos = compositionDao.findAll(
				  PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "lastUpdate")));
		return compos.toList();
	}
	
}
