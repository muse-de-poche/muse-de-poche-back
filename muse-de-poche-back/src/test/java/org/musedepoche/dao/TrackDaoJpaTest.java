package org.musedepoche.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.musedepocheapi.config.ApplicationConfigTest;
import org.musedepocheapi.dao.ICompositionDao;
import org.musedepocheapi.dao.ISoundDao;
import org.musedepocheapi.dao.ITrackDao;
import org.musedepocheapi.model.Composition;
import org.musedepocheapi.model.Sound;
import org.musedepocheapi.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigTest.class)
@Transactional
public class TrackDaoJpaTest {

	@Autowired
	ITrackDao trackDao;
	
	@Autowired
	ICompositionDao compositionDao;
	
	@Autowired
	ISoundDao soundDao;
	
	@Test
	public void trackDaoCreate() {
		Track track1 = new Track(150, "guitare", null, null);
		track1 = trackDao.save(track1);
		
		Assert.assertNotNull(track1);
		Assert.assertEquals("guitare", track1.getInstrument());
		Assert.assertEquals(150, track1.getDuration());
	}
	
	@Test
	public void trackDaoUpdate() {
		Composition compo = new Composition(new Date(), 50, null, null);
		compo = compositionDao.save(compo);
		Track track1 = new Track();
		track1.setDuration(120);
		track1.setInstrument("voice");
		track1 = trackDao.save(track1);
		track1.setComposition(compo);
		
		trackDao.save(track1);
		
		Track findTrack = trackDao.findById(track1.getId()).get();
		
		Assert.assertNotNull(findTrack.getComposition());
	}
	
	@Test
	public void trackDaoAddSounds() {
		Composition compo = new Composition(new Date(), 50, null, null);
		compo = compositionDao.save(compo);
		Track track1 = new Track();
		track1.setDuration(120);
		track1.setInstrument("voice");
		track1 = trackDao.save(track1);
		track1.setComposition(compo);
		
		Sound sound1 = new Sound();
		Sound sound2 = new Sound();
		List<Sound> sounds = new ArrayList<Sound>();
		sounds.add(sound2);
		sounds.add(sound1);
		
		track1.addAll(sounds);
		track1.add(new Sound());
		
		trackDao.save(track1);
		
		Track findTrack = trackDao.findById(track1.getId()).get();
		
		Assert.assertNotNull(findTrack.getComposition());
		Assert.assertEquals(3, findTrack.getSounds().size());
	}
}
