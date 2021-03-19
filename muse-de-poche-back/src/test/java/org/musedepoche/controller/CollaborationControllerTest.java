package org.musedepoche.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composer;
import org.musedepoche.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CollaborationControllerTest extends DataDao {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void shloudGetErrorsIfCollaborationIsNotHydrate() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration());

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldGetErrorIfCompositionIsNull() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration(null, new Composer(), "Lorem ipsum"));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldGetErrorIfComposerIsNull() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration(new Composition(), null, "Lorem ipsum"));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldGetErrorIfTextIsNull() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(), null));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldGetErrorIfTextIsEmpty() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(), ""));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl)).andExpect(status().isBadRequest());
	}

	@Test
	public void shouldGetErrorIfTextIsBlank() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(), "            "));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldGetErrorIfTextIsTooLong() throws Exception {
		String jsonColl = this.mapper.writeValueAsString(new Collaboration(new Composition(), new Composer(), "Curabitur sit amet dignissim risus. Curabitur sed aliquet enim. Aliquam sodales nibh ut dolor convallis egestas. Maecenas condimentum leo id est mattis fringilla. Pellentesque pulvinar tortor eu leo semper, id viverra nunc tempus. Curabitur porta velit eros, vitae condimentum orci laoreet sodales. Proin dictum bibendum elementum. In magna nisl, molestie at sapien at, sodales cursus lorem tincidunt."));

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTest() throws Exception {
		Collaboration c = new Collaboration();
		Composer composer = super.composerDao.findByPseudo("Jmarc").get();
		Composition composition = super.compositionDao
				.findByOwner(super.composerDao.findByPseudo("Btyne").get().getId()).get(0);


		c = new Collaboration(composition, composer, "Lorem ipsum");
		String jsonColl = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonColl))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.demandDate", is(notNullValue())))
				.andExpect(jsonPath("$.composition", is(notNullValue())))
				.andExpect(jsonPath("$.composer", is(notNullValue()))).andExpect(jsonPath("$.status").value("WAITING"))
				.andExpect(jsonPath("$.right").value("READONLY")).andExpect(jsonPath("$.text").value("Lorem ipsum"));
	}

}
