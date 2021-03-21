package org.musedepoche.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composer;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Message;
import org.musedepoche.model.Right;
import org.musedepoche.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Cyril R.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest extends DataDao {

	@Autowired
	private MockMvc mockMvc;

	@Test /* Create */
	public void shloudGetErrorsIfMessageIsNotHydrate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writeValueAsString(new Message());

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfComposerIsNull() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writeValueAsString(new Message(null, new Composition(), "Lorem ipsum"));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfCompositionIsNull() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writeValueAsString(new Message(new Composer(), null, "Lorem ipsum"));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsNull() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writeValueAsString(new Message(new Composer(), new Composition(), null));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsEmpty() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writeValueAsString(new Message(new Composer(), new Composition(), ""));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsBlank() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper
				.writeValueAsString(new Message(new Composer(), new Composition(), "     "));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsTooLong() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writeValueAsString(new Message(new Composer(), new Composition(),
				"Curabitur sit amet dignissim risus. Curabitur sed aliquet enim. Aliquam sodales nibh ut dolor convallis egestas. Maecenas condimentum leo id est mattis fringilla. Pellentesque pulvinar tortor eu leo semper, id viverra nunc tempus. Curabitur porta velit eros, vitae condimentum orci laoreet sodales. Proin dictum bibendum elementum. In magna nisl, molestie at sapien at, sodales cursus lorem tincidunt."));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldCreateMessageWithoutError() throws Exception {
		Composer composer = super.composers.get(3); // Axel
		Composition composition = super.compositions.get(0); // Composition's Jean

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonMsg = mapper.writerWithView(IViews.IViewMessageDetail.class)
				.writeValueAsString(new Message(composer, composition, "Lorem ipsum"));

		mockMvc.perform(post("/messages").contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.sendingDate", is(notNullValue())))
				.andExpect(jsonPath("$.sender", is(notNullValue())))
				.andExpect(jsonPath("$.subject", is(notNullValue())))
				.andExpect(jsonPath("$.text").value("Lorem ipsum"));
	}

	@Test /* GetById */
	public void shouldGetMessageById() throws Exception {
		Message message = super.messages.get(0);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonMsg = mapper.writerWithView(IViews.IViewMessage.class)
				.writeValueAsString(message.getId());

		mockMvc.perform(get("/messages/" + jsonMsg)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(message.getId()))
				.andExpect(jsonPath("$.text").value(message.getText()));
	}

	@Test /* GetById */
	public void shouldGetErrorById() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonMsg = mapper.writerWithView(IViews.IViewMessage.class).writeValueAsString(Long.MAX_VALUE);

		mockMvc.perform(get("/messages/" + jsonMsg)).andExpect(status().isNotFound());
	}

	@Test /* GetDetailById */
	public void shouldGetMessageDetailById() throws Exception {
		Message message = super.messages.get(0);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonMsg = mapper.writerWithView(IViews.IViewMessageDetail.class)
				.writeValueAsString(message.getId());

		mockMvc.perform(get("/messages/" + jsonMsg + "/detail")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(message.getId()))
				.andExpect(jsonPath("$.sendingDate", is(notNullValue())))
				.andExpect(jsonPath("$.sender", is(notNullValue())))
				.andExpect(jsonPath("$.subject", is(notNullValue())))
				.andExpect(jsonPath("$.text").value(message.getText()));
	}

	@Test /* GetDetailById */
	public void shouldGetErrorByIdWithDetail() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		String jsonMsg = mapper.writerWithView(IViews.IViewMessageDetail.class).writeValueAsString(Long.MAX_VALUE);

		mockMvc.perform(get("/messages/" + jsonMsg + "/detail")).andExpect(status().isNotFound());
	}

	@Test /* Update */
	public void shouldBeOKWhenUpdate() throws Exception {
		Message message = super.messages.get(0);
		message.setSendingDate(new Date());
		message.setText("Mea Maxima Culpa");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		String jsonMsg = mapper.writerWithView(IViews.IViewMessageDetail.class).writeValueAsString(message);

		mockMvc.perform(put("/messages/"+message.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(message.getId()))
				.andExpect(jsonPath("$.text").value(message.getText()));
	}

	@Test /* Update */
	public void shouldGetErrorIfIdNotExistWhenUpdate() throws Exception {
		Message message = super.messages.get(0);
		message.setSendingDate(new Date());
		message.setText("Mea Maxima Culpa");

		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = mapper.writerWithView(IViews.IViewMessage.class).writeValueAsString(message);

		mockMvc.perform(put("/messages/"+Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON).content(jsonMsg))
				.andExpect(status().isNotFound());
	}
	
	@Test /* bySender */
	public void shouldGetMessagesByComposerId() throws Exception {
		Composer composer = super.composers.get(1); // Bea
		int size = (int) super.messages.stream().filter(m -> m.getSender().getId().equals(composer.getId())).count();
		
		mockMvc.perform(get("/messages/bySender/"+composer.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(size)));
	}
	
	@Test /* bySender */
	public void shouldGetEmptyListByComposerIdIfIdNotExist() throws Exception {
		mockMvc.perform(get("/messages/bySender/"+Long.MAX_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test /* byComposition */
	public void shouldGetMessagesByCompositionId() throws Exception {
		Composition composition = super.compositions.get(1); // Bea's composition
		int size = (int) super.messages.stream().filter(m -> m.getSubject().getId().equals(composition.getId())).count();
		
		mockMvc.perform(get("/messages/bySubject/"+composition.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(size)));
	}
	
	@Test /* byComposition */
	public void shouldGetEmptyListByCompositionIdIfIdNotExist() throws Exception {
		mockMvc.perform(get("/messages/bySubject/"+Long.MAX_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}

}
