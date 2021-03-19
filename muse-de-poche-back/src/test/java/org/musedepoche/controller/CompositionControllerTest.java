package org.musedepoche.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.musedepoche.dao.DataDao;
import org.musedepoche.model.Composer;
import org.musedepoche.model.Composition;
import org.musedepoche.model.IViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CompositionControllerTest extends DataDao {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void compositionGetAll() throws Exception {
		mockMvc.perform(get("/composition")).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()").value(numberOfComposition));
	}
	
	@Test
	public void compositionGetById() throws Exception {
		mockMvc.perform(get("/composition/8")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.title").value("compo2")).andExpect(jsonPath("$.id").value(8))
		.andExpect(jsonPath("playsNumber").value(658495)).andExpect(jsonPath("owner.pseudo").value("Btyne"));
		
		mockMvc.perform(get("/composition/2")).andExpect(status().isNotFound());
	}
	
	@Test
	public void compositionsBycomposer() throws Exception {
		mockMvc.perform(get("/composition/composer/1")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.length()").value(2));
	}
	
	@Test
	public void compositionCreate() throws Exception {
		Composer bea =  this.composerDao.findById(2L).get();
		Composition composition = new Composition("compo8", 50, bea);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonComposer = mapper.writerWithView(IViews.IViewCompositionDetail.class).writeValueAsString(composition);
		
		mockMvc.perform(post("/composition").contentType(MediaType.APPLICATION_JSON).content(jsonComposer))
			.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
			.andExpect(jsonPath("$.title").value("compo8")).andExpect(jsonPath("$.playsNumber").value(50))
			.andExpect(jsonPath("$.owner.pseudo").value("Btyne"));
	}
	
	@Test
	public void compositionUpdate() throws Exception {
		Composer bea =  this.composerDao.findById(2L).get();
		Composition compo = new Composition("compoUpdate", 50, bea);
		

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		String jsonComposer = mapper.writerWithView(IViews.IViewCompositionDetail.class).writeValueAsString(compo);
	}
}
