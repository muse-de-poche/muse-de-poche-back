package org.musedepoche.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.musedepoche.dao.ICollaborationDao;
import org.musedepoche.dao.ICompositionDao;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.musedepoche.validator.CollaborationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Cyril R.
 */
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/workgroups")
public class CollaborationController {

	@Autowired
	private ICollaborationDao collaborationDao;
	
	@Autowired
	private ICompositionDao compositionDao;

	@Autowired
	private CollaborationValidator collaborationValidator;

	@PostMapping
	public Collaboration create(@Valid @RequestBody Collaboration collaboration, BindingResult result) {
		this.collaborationValidator.validate(collaboration, result);

		if (result.hasErrors()) {
			String errors = result.getAllErrors().stream().map(e -> "[" + e.getCode() + "] " + e.getDefaultMessage())
					.collect(Collectors.joining("\n"));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors);
		}
		
		/* Ne doit pas le propriètaire */
		if (this.compositionDao.isOwnerOf(collaboration.getComposer().getId(), collaboration.getComposition().getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le compositeur [" + collaboration.getComposer().getId() + "] est le propriètaire de la composition [" + collaboration.getComposition().getId() + "]");
		}

		return this.collaborationDao.save(collaboration);
	}

	@GetMapping("/{id}")
	@JsonView(IViews.IViewCollaboration.class)
	public Collaboration simple(@PathVariable Long id) {
		Optional<Collaboration> collaboration = this.collaborationDao.findById(id);

		if (collaboration.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "[" + id + "] not exist");
		} 

		return collaboration.get();
	}
	
	@GetMapping("/{id}/detail")
	@JsonView(IViews.IViewCollaborationDetail.class)
	public Collaboration detail(@PathVariable Long id) {
		Optional<Collaboration> collaboration = this.collaborationDao.findById(id);

		if (collaboration.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "[" + id + "] not exist");
		} 

		return collaboration.get();
	}
	
	@PutMapping("/{id}")
	@JsonView(IViews.IViewCollaborationDetail.class)
	public Collaboration update(@PathVariable Long id, @RequestBody Collaboration collaboration) {
		if (!this.collaborationDao.existsById(id) || !id.equals(collaboration.getId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "[" + id + "] not exist");
		}

		this.collaborationDao.save(collaboration);
		
		return this.collaborationDao.findById(id).get();
	}
	
	@GetMapping("/byComposer/{id}")
	@JsonView(IViews.IViewCollaborationByComposer.class)
	public List<Collaboration> byComposer(@PathVariable Long id) {
		return this.collaborationDao.findByComposer(id);
	}

	@GetMapping("/byComposition/{id}")
	@JsonView(IViews.IViewCollaborationByComposition.class)
	public List<Collaboration> byComposition(@PathVariable Long id) {
		return this.collaborationDao.findByComposition(id);
	}

}
