package org.musedepoche.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.Composer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ComposerControllerTest extends DataDao {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void composerGetAll() throws Exception {
		mockMvc.perform(get("/composer")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(numberOfComposer));
	}

	@Test
	public void composerGetById() throws Exception {
		mockMvc.perform(get("/composer/2")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.pseudo").value("Btyne")).andExpect(jsonPath("$.password").value("Btyne123"))
				.andExpect(jsonPath("$.lastname").value("Bea")).andExpect(jsonPath("$.firstname").value("Tyne"))
				.andExpect(jsonPath("$.country").value("France"))
				.andExpect(jsonPath("$.email").value("bea.tyne@gmail.com"));
	}

	@Test
	public void composerGetByIdDetail() throws Exception {
		mockMvc.perform(get("/composer/2/detail")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.pseudo").value("Btyne")).andExpect(jsonPath("$.password").value("Btyne123"))
				.andExpect(jsonPath("$.lastname").value("Bea")).andExpect(jsonPath("$.firstname").value("Tyne"))
				.andExpect(jsonPath("$.country").value("France"))
				.andExpect(jsonPath("$.email").value("bea.tyne@gmail.com"));
	}

	@Test
	public void composerGetByPseudo() throws Exception {
		mockMvc.perform(get("/composer/Atune/pseudo")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(4))
				.andExpect(jsonPath("$.pseudo").value("Atune")).andExpect(jsonPath("$.password").value("Atune123"))
				.andExpect(jsonPath("$.lastname").value("Axel")).andExpect(jsonPath("$.firstname").value("Tune"))
				.andExpect(jsonPath("$.country").value("France"))
				.andExpect(jsonPath("$.email").value("axel.tune@gmail.com"));
	}

	@Test
	public void composerCreate() throws Exception {
		Date date = new Date();
		Composer composer = new Composer("Lpiet", "Lpiet123", "Laure", "Piet", "France", "laure.piet@gmail.com", date);

		ObjectMapper mapper = new ObjectMapper();
		String jsonComposer = mapper.writeValueAsString(composer);

		mockMvc.perform(post("/composer").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.pseudo").value("Lpiet")).andExpect(jsonPath("$.password").value("Lpiet123"))
				.andExpect(jsonPath("$.lastname").value("Laure")).andExpect(jsonPath("$.firstname").value("Piet"))
				.andExpect(jsonPath("$.country").value("France"))
				.andExpect(jsonPath("$.email").value("laure.piet@gmail.com"));
		super.numberOfComposer++;
	}

	@Test
	public void composerUpdate() throws Exception {
		Composer composer = new Composer(5L, "Cpierre", "Cpierre123", "Celine", "Pierre", "Belgique",
				"celine.pierre@gmail.com");

		ObjectMapper mapper = new ObjectMapper();
		String jsonComposer = mapper.writeValueAsString(composer);

		mockMvc.perform(put("/composer/5").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.pseudo").value("Cpierre")).andExpect(jsonPath("$.password").value("Cpierre123"))
				.andExpect(jsonPath("$.lastname").value("Celine")).andExpect(jsonPath("$.firstname").value("Pierre"))
				.andExpect(jsonPath("$.country").value("Belgique"))
				.andExpect(jsonPath("$.email").value("celine.pierre@gmail.com"));
	}

	@Test
	public void composerDelete() throws Exception {
		Composer composer = new Composer("Cpierre", "Cpierre123", "Celine", "Pierre", "Belgique",
				"celine.pierre@gmail.com", new Date());
		composer = composerDao.save(composer);
		mockMvc.perform(delete("/composer/"+composer.getId())).andExpect(status().isOk());

	}
}
