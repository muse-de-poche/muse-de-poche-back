package org.musedepoche.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
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
public class CollaborationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createTest() throws Exception {
		Collaboration c = new Collaboration();

		ObjectMapper mapper = new ObjectMapper();
		String jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(null, new Composer(), "Lorem ipsum");
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(new Composition(), null, "Lorem ipsum");
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(new Composition(), new Composer(), null);
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(new Composition(), new Composer(), "");
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(new Composition(), new Composer(), "     ");
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(new Composition(), new Composer(),
				"Curabitur sit amet dignissim risus. Curabitur sed aliquet enim. Aliquam sodales nibh ut dolor convallis egestas. Maecenas condimentum leo id est mattis fringilla. Pellentesque pulvinar tortor eu leo semper, id viverra nunc tempus. Curabitur porta velit eros, vitae condimentum orci laoreet sodales. Proin dictum bibendum elementum. In magna nisl, molestie at sapien at, sodales cursus lorem tincidunt.");
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/workgroups").contentType(MediaType.APPLICATION_JSON).content(jsonComposer)).andExpect(status().isBadRequest());

		c = new Collaboration(new Composition(), new Composer(), "Lorem ipsum");
		jsonComposer = mapper.writeValueAsString(c);

		mockMvc.perform(post("/composer").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.demandeDate", is(notNullValue())))
				.andExpect(jsonPath("$.composition", is(notNullValue())))
				.andExpect(jsonPath("$.composer", is(notNullValue()))).andExpect(jsonPath("$.status").value("WAITING"))
				.andExpect(jsonPath("$.right").value("READONLY")).andExpect(jsonPath("$.text").value("Lorem ipsum"));
	}

}
