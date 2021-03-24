package org.musedepoche.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Sound;
import org.musedepoche.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TrackControllerTest extends DataDao {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void trackGetById() throws Exception {
		mockMvc.perform(get("/track/31")).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(31)).andExpect(jsonPath("$.duration").value(0))
			.andExpect(jsonPath("$.instrument").value("guitare"));
	}
	
	@Test
	public void tracksGetByComposition() throws Exception {
		mockMvc.perform(get("/track/composition/7")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()").value(4));
		
		mockMvc.perform(get("/track/composition/45")).andExpect(status().isNotFound());
		mockMvc.perform(get("/track/composition/10")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
	}
	
	@Test
	public void trackCreate() throws Exception {
		Composition compo = compositionDao.findById(11L).get();
		Track track = new Track(0, "guitare", null, compo);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonTrack = mapper.writerWithView(IViews.IViewTrack.class).writeValueAsString(track);
		System.out.println(jsonTrack);
		mockMvc.perform(post("/track").contentType(MediaType.APPLICATION_JSON).content(jsonTrack))
			.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
			.andExpect(jsonPath("$.duration").value(0)).andExpect(jsonPath("$.instrument").value("guitare"));
		
		
		mockMvc.perform(get("/composition/11")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.tracks.length()").value(3)).andDo(print());
	}
	
	@Test
	public void trackUpdate() throws Exception {
		Track track = trackDao.findById(38L).get();
		track.setDuration(200);
		track.setSounds(new ArrayList<Sound>());

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonTrack = mapper.writerWithView(IViews.IViewTrack.class).writeValueAsString(track);
		
		mockMvc.perform(put("/track/38").contentType(MediaType.APPLICATION_JSON).content(jsonTrack))
			.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
			.andExpect(jsonPath("$.duration").value(200));
		
		mockMvc.perform(put("/track/32").contentType(MediaType.APPLICATION_JSON).content(jsonTrack))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void trackDelete() throws Exception {
		Composition compo = compositionDao.findById(11L).get();
		Track track = new Track(0, "guitare", null, compo);
		track = trackDao.save(track);
		System.out.println(track);
		mockMvc.perform(delete("/track/"+ track.getId())).andExpect(status().isOk());
		
		mockMvc.perform(delete("/track/"+ track.getId())).andExpect(status().isNotFound());
	}
}
