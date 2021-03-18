package org.musedepoche.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.musedepoche.dao.IMessageDao;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Message;
import org.musedepoche.validator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author Cyril R.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private IMessageDao messageDao;

	@Autowired
	private MessageValidator messageValidator;

	@PostMapping
	public Message create(@Valid @RequestBody Message message, BindingResult result) {
		this.messageValidator.validate(message, result);

		if (result.hasErrors()) {
			String errors = result.getAllErrors().stream().map(e -> "[" + e.getCode() + "] " + e.getDefaultMessage())
					.collect(Collectors.joining("\n"));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors);
		}

		return this.messageDao.save(message);
	}

	@GetMapping("/{id}")
	@JsonView(IViews.IViewMessage.class)
	public Message simple(@PathVariable Long id) {
		Optional<Message> collaboration = this.messageDao.findById(id);

		if (collaboration.isPresent()) {
			return collaboration.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "[" + id + "] not exist");
		}
	}
	
	@GetMapping("/{id}/detail")
	@JsonView(IViews.IViewMessageDetail.class)
	public Message detail(@PathVariable Long id) {
		Optional<Message> collaboration = this.messageDao.findById(id);

		if (collaboration.isPresent()) {
			return collaboration.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "[" + id + "] not exist");
		}
	}
	
	@GetMapping("/bySender/{id}")
	@JsonView(IViews.IViewMessageDetail.class)
	public List<Message> byComposer(@PathVariable Long id) {
		return this.messageDao.findBySender(id);
	}

	@GetMapping("/bySubject/{id}")
	@JsonView(IViews.IViewMessageDetail.class)
	public List<Message> byComposition(@PathVariable Long id) {
		return this.messageDao.findBySubject(id);
	}

}
