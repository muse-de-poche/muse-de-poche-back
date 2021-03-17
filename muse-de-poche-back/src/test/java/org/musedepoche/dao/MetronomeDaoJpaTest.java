package org.musedepoche.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.musedepocheapi.config.ApplicationConfigTest;
import org.musedepocheapi.dao.IMetronomeDao;
import org.musedepocheapi.model.Metronome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigTest.class)
public class MetronomeDaoJpaTest {

	@Autowired
	IMetronomeDao metronomeDao;
	
	@Test
	public void metronomeDaoCreate() {
		Metronome metronome = new Metronome();
		
		metronome.setBpm(80);
		metronome.setClickType(3);
		metronome.setMetric("12/8");
		
		metronome = metronomeDao.save(metronome);
		
		Metronome metro2 = new Metronome(metronome.getId(), metronome.getMetric(), metronome.getClickType(), metronome.getBpm());
		
		Assert.assertEquals(80, metro2.getBpm());
		Assert.assertEquals(metronome.toString(), metro2.toString());
	}
}
