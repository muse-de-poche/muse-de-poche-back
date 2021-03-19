package org.musedepoche.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Metronome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MetronomeControllerTest extends DataDao {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void metronomeGetById() throws Exception {
		mockMvc.perform(get("/metronome/27")).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(27)).andExpect(jsonPath("$.clickType").value(1))
			.andExpect(jsonPath("$.metric").value("4/4")).andExpect(jsonPath("$.bpm").value(120));
	}
	
	@Test
	public void metronomeGetByComposition() throws Exception {
		mockMvc.perform(get("/metronome/composition/7")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", is(notNullValue())))
		.andExpect(jsonPath("$.id").value(27)).andExpect(jsonPath("$.metric").value("4/4"));
		
		mockMvc.perform(get("/metronome/composition/9")).andExpect(status().isNotFound());
	}
	
	@Test
	public void metronomeCreate() throws Exception {
		Metronome metronome = new Metronome("7/4", 2, 75);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonComposer = mapper.writerWithView(IViews.IViewCompositionDetail.class).writeValueAsString(metronome);
		
		mockMvc.perform(post("/metronome").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
			.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
			.andExpect(jsonPath("$.metric").value("7/4")).andExpect(jsonPath("$.clickType").value(2))
			.andExpect(jsonPath("$.bpm").value(75));
	}
	
	@Test
	public void metronomeUpdate() throws Exception {
		Metronome metronome = metronomeDao.findById(27L).get();
		metronome.setBpm(200);

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonComposer = mapper.writerWithView(IViews.IViewCompositionDetail.class).writeValueAsString(metronome);
		
		mockMvc.perform(put("/metronome/27").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
			.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
			.andExpect(jsonPath("$.bpm").value(200)).andExpect(jsonPath("$.metric").value("4/4"));
		
		mockMvc.perform(put("/metronome/32").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void metronomeDelete() throws Exception {
		Metronome metronome = new Metronome("7/4", 2, 75);
		metronome = metronomeDao.save(metronome);
		mockMvc.perform(delete("/metronome/"+ metronome.getId())).andExpect(status().isOk());
		
		mockMvc.perform(delete("/metronome/"+ metronome.getId())).andExpect(status().isNotFound());
	}
}
