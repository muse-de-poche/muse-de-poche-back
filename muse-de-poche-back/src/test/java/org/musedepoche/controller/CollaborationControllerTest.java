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

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composer;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
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
public class CollaborationControllerTest extends DataDao {

	@Autowired
	private MockMvc mockMvc;

	@Test /* Create */
	public void shloudGetErrorsIfCollaborationIsNotHydrate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writeValueAsString(new Collaboration());

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfCompositionIsNull() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writeValueAsString(new Collaboration(null, new Composer(), "Lorem ipsum"));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfComposerIsNull() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writeValueAsString(new Collaboration(new Composition(), null, "Lorem ipsum"));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsNull() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(), null));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsEmpty() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(), ""));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsBlank() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper
				.writeValueAsString(new Collaboration(new Composition(), new Composer(), "            "));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetErrorIfTextIsTooLong() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(),
				"Curabitur sit amet dignissim risus. Curabitur sed aliquet enim. Aliquam sodales nibh ut dolor convallis egestas. Maecenas condimentum leo id est mattis fringilla. Pellentesque pulvinar tortor eu leo semper, id viverra nunc tempus. Curabitur porta velit eros, vitae condimentum orci laoreet sodales. Proin dictum bibendum elementum. In magna nisl, molestie at sapien at, sodales cursus lorem tincidunt."));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* Create */
	public void shouldGetCollaborationWithoutError() throws Exception {
		Composer composer = super.composers.get(1); // Bea
		Composition composition = super.compositions.get(6); // Composition's JC

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonColl = mapper.writerWithView(IViews.IViewCollaborationDetail.class)
				.writeValueAsString(new Collaboration(composition, composer, "Lorem ipsum"));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.demandDate", is(notNullValue())))
				.andExpect(jsonPath("$.composition", is(notNullValue())))
				.andExpect(jsonPath("$.composer", is(notNullValue()))).andExpect(jsonPath("$.status").value("WAITING"))
				.andExpect(jsonPath("$.right").value("READONLY")).andExpect(jsonPath("$.text").value("Lorem ipsum"));
	}

	@Test /* Create */
	public void shouldGetErrorIfComposerIsOwnerOfHisComposition() throws Exception {
		Composer composer = super.composers.get(0); // Jean
		Composition composition = super.compositions.get(0); // Composition's Jean

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonColl = mapper.writerWithView(IViews.IViewCollaborationDetail.class)
				.writeValueAsString(new Collaboration(composition, composer, "Lorem ipsum"));

		System.out.println(jsonColl);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test /* GetById */
	public void shouldGetCollaborationById() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonColl = mapper.writerWithView(IViews.IViewCollaboration.class)
				.writeValueAsString(super.collaborations.get(1).getId());

		mockMvc.perform(get("/workgroups/" + jsonColl)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(super.collaborations.get(1).getId()))
				.andExpect(jsonPath("$.demandDate").exists()).andExpect(jsonPath("$.right").value("READONLY"))
				.andExpect(jsonPath("$.status").value("ACCEPTED")).andExpect(jsonPath("$.text").value(
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra sed nisi nec dapibus. Duis et nulla id."));
	}

	@Test /* GetById */
	public void shouldGetErrorById() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonColl = mapper.writerWithView(IViews.IViewCollaboration.class).writeValueAsString(Long.MAX_VALUE);

		mockMvc.perform(get("/workgroups/" + jsonColl)).andExpect(status().isNotFound());
	}

	@Test /* GetDetailById */
	public void shouldGetCollaborationDetailById() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		Collaboration collaboration = super.collaborations.get(1);
		String jsonColl = mapper.writerWithView(IViews.IViewCollaborationDetail.class)
				.writeValueAsString(collaboration.getId());

		mockMvc.perform(get("/workgroups/" + jsonColl + "/detail")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(collaboration.getId()))
				.andExpect(jsonPath("$.demandDate").exists())
				.andExpect(jsonPath("$.composition").exists()).andExpect(jsonPath("$.composer").exists())
				.andExpect(jsonPath("$.right").value("READONLY")).andExpect(jsonPath("$.status").value("ACCEPTED"))
				.andExpect(jsonPath("$.text").value(
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra sed nisi nec dapibus. Duis et nulla id."));
	}

	@Test /* GetDetailById */
	public void shouldGetErrorByIdWithDetail() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		String jsonColl = mapper.writerWithView(IViews.IViewCollaboration.class).writeValueAsString(Long.MAX_VALUE);

		mockMvc.perform(get("/workgroups/" + jsonColl + "/detail")).andExpect(status().isNotFound());
	}

	@Test /* Update */
	public void shouldBeOKWhenUpdate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		Collaboration collaboration = super.collaborations.get(0);
		collaboration.setStatus(Status.BANNED);
		collaboration.setRight(Right.WRITE);

		String jsonColl = mapper.writerWithView(IViews.IViewCollaboration.class).writeValueAsString(collaboration);

		mockMvc.perform(put("/workgroups/"+collaboration.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(collaboration.getId()))
				.andExpect(jsonPath("$.status").value("BANNED"))
				.andExpect(jsonPath("$.right").value("WRITE"));
	}

	@Test /* Update */
	public void shouldGetErrorIfIdNotExistWhenUpdate() throws Exception {
		Collaboration collaboration = super.collaborations.get(0);
		collaboration.setStatus(Status.BANNED);


		ObjectMapper mapper = new ObjectMapper();
		String jsonColl = mapper.writerWithView(IViews.IViewCollaboration.class).writeValueAsString(collaboration);

		mockMvc.perform(put("/workgroups/"+Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isNotFound());
	}
	
	@Test /* byComposer */
	public void shouldGetCollaborationsByComposerId() throws Exception {
		Composer composer = super.composers.get(0);
		int size = (int) super.collaborations.stream().filter(c -> c.getComposer().getId().equals(composer.getId())).count();
		
		mockMvc.perform(get("/workgroups/byComposer/"+composer.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(size)));
	}
	
	@Test /* byComposer */
	public void shouldGetEmptyListByComposerIdIfIdNotExist() throws Exception {
		mockMvc.perform(get("/workgroups/byComposer/"+Long.MAX_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test /* byComposition */
	public void shouldGetCollaborationsByCompositionId() throws Exception {
		Composition composition = super.compositions.get(1);
		int size = (int) super.collaborations.stream().filter(c -> c.getComposition().getId().equals(composition.getId())).count();
		
		mockMvc.perform(get("/workgroups/byComposition/"+composition.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(size)));
	}
	
	@Test /* byComposition */
	public void shouldGetEmptyListByCompositionIdIfIdNotExist() throws Exception {
		mockMvc.perform(get("/workgroups/byComposition/"+Long.MAX_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}

}
