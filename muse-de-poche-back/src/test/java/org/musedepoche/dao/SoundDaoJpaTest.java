package org.musedepoche.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.musedepocheapi.config.ApplicationConfigTest;
import org.musedepocheapi.dao.ISoundDao;
import org.musedepocheapi.dao.ITrackDao;
import org.musedepocheapi.model.Sound;
import org.musedepocheapi.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigTest.class)
public class SoundDaoJpaTest {

	@Autowired
	ISoundDao soundDao;
	
	@Autowired
	ITrackDao trackDao;
	
	@Test
	public void soundDaoCreate() {
		Track track = new Track();
		track = trackDao.save(track);
		Sound sound = new Sound(50, "guitar.mp3", track);
		
		sound = soundDao.save(sound);
		
		Assert.assertEquals((Long)2L, sound.getId());
		Assert.assertEquals(50, sound.getPosition());
		Assert.assertEquals("guitar.mp3", sound.getFile());
		Assert.assertNotNull(sound.getTrack());
	}
	
	@Test
	public void soundDaoUpdate() {
		Sound sound = new Sound();
		sound.setFile("bass.wav");
		sound.setPosition(3);
		sound.setTrack(null);
		
		sound = soundDao.save(sound);
		
		sound.setPosition(5);
		Sound savedSound = soundDao.save(sound);
		
		Assert.assertEquals(5, savedSound.getPosition());
	}
}
