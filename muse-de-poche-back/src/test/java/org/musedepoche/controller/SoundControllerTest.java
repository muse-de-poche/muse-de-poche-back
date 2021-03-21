package org.musedepoche.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.dao.ISoundDao;
import org.musedepoche.model.IViews;
import org.musedepoche.model.Sound;
import org.musedepoche.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SoundControllerTest extends DataDao {

	@Autowired
	private ISoundDao soundDao;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void soundCreate() throws Exception {
		Path path = Paths.get("src/test/resources/sample.wav");
		MockMultipartFile req  = new MockMultipartFile("file", "sample.wav", "audio/mpeg", Files.readAllBytes(path));
		
		mockMvc.perform(multipart("/sound/31").file(req)).andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("31-guitare2")).andExpect(jsonPath("$.id").value(42));
		
		mockMvc.perform(multipart("/sound/21").file(req)).andExpect(status().isNotFound());
	}
	
	@Test
	public void findSound() throws Exception {				
		mockMvc.perform(get("/sound/40")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(40));
	}
	
	@Test
	public void deleteSound() throws Exception {
		Track track = trackDao.findById(38L).get();
		Path path = Paths.get("src/test/resources/sample.wav");
		Sound sound = new Sound(0,Files.readAllBytes(path), track);
		sound = soundDao.save(sound);
		
		mockMvc.perform(delete("/sound/"+sound.getId())).andExpect(status().isOk());
		mockMvc.perform(delete("/sound/"+sound.getId())).andExpect(status().isNotFound());
	}
	
	@Test
	public void updateSoundData() throws Exception {
//		Sound sound = trackDao.findById(31L).get().getSounds().get(0);
		Sound sound = soundDao.findById(40L).get();
		sound.setName("31-guitareX");
//		sound = soundDao.save(sound);
		ObjectMapper mapper = new ObjectMapper();
//		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonSound = mapper.writerWithView(IViews.IViewTrack.class).writeValueAsString(sound);
		
		mockMvc.perform(patch("/sound/update/data/40").contentType(MediaType.APPLICATION_JSON).content(jsonSound)).andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("31-guitareX"));
		
		mockMvc.perform(patch("/sound/update/data/30").contentType(MediaType.APPLICATION_JSON).content(jsonSound)).andExpect(status().isNotFound());
	}
	
	@Test
	public void updateSound() throws Exception {
		Path path = Paths.get("src/test/resources/sample.wav");
		MockMultipartFile req  = new MockMultipartFile("file", "sample.wav", "audio/mpeg", Files.readAllBytes(path));
		
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/sound/update/file/40");
		builder.with(new RequestPostProcessor() {
			
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});
		
		mockMvc.perform(builder.file(req)).andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("31-guitareX")).andExpect(jsonPath("$.id").value(40));
		
		MockMultipartHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.multipart("/sound/update/file/30");
		builder2.with(new RequestPostProcessor() {
			
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});
		
		mockMvc.perform(builder2.file(req)).andExpect(status().isNotFound());
	}
	
}
