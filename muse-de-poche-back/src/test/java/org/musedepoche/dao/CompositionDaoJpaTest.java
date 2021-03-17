package org.musedepoche.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composition;
import org.musedepoche.model.Message;
import org.musedepoche.model.Metronome;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CompositionDaoJpaTest extends DataDao {

	@Test
	public void compositionDaoCreate() {

		Composer composer = new Composer("Jean", "Dupont", "France", "jeandupont@mail.fr", new Date(), null, null);
		composer = composerDao.save(composer);

		Metronome metronome = new Metronome("4/4", 1, 120);
		metronome = metronomeDao.save(metronome);
		Composition compo1 = new Composition(new Date(), 50, composer, null);
		compo1.setMetronome(metronome);
		compo1 = compositionDao.save(compo1);
		assertEquals(50, compo1.getPlaysNumber());
		assertEquals("Dupont", compo1.getOwner().getFirstname());
		assertEquals(120, compo1.getMetronome().getBpm());
		assertEquals("4/4", compo1.getMetronome().getMetric());
		assertEquals(1, compo1.getMetronome().getClickType());
	}

	@Test
	public void compositionDaoUpdate() {
		Composer composer = new Composer();
		composer.setLastname("Jean");
		composer.setFirstname("Dupont");
		composer.setCountry("France");
		composer.setEmail("jeandupont@mail.fr");
		composer.setSubscribedDate(new Date());

		composer = composerDao.save(composer);

		Composition compo1 = new Composition();
		compo1.setCreatedDate(new Date());
		compo1.setPlaysNumber(50);
		compo1.setOwner(composer);

		compo1 = compositionDao.save(compo1);

		Collaboration collab = new Collaboration(new Date(), null, null, compo1, composer, Status.WAITING,
				Right.READONLY);
		collab = collaborationDao.save(collab);

		compo1.addCollaboration(collab);

		compositionDao.save(compo1);

		Composition compoSaved = compositionDao.findById(compo1.getId()).get();

		assertEquals(1, compoSaved.getCollaborations().size());
	}

	@Test
	public void compositionDaoDelete() {
		Composition compo2 = new Composition(new Date(), 50, null, null);
		compo2 = compositionDao.save(compo2);
		Composition compo3 = new Composition(new Date(), 50, null, null);
		compo3 = compositionDao.save(compo3);
		Composition compo1 = new Composition(new Date(), 50, null, null);
		compo1 = compositionDao.save(compo1);

		compositionDao.deleteById(compo1.getId());

		List<Composition> listCompos = compositionDao.findAll();

		assertEquals(2, listCompos.size());
	}

	@Test
	public void compositionDaoAddTracks() {
		Composition compo1 = new Composition(new Date(), 50, null, null);
		Track track1 = new Track();
		Track track2 = new Track();
		track1.setDuration(100);
		track2.setDuration(200);
		compo1.add(track2);
		compo1.add(0, track1);
		compo1 = compositionDao.save(compo1);

		Composition compoFind = compositionDao.findById(compo1.getId()).get();
		Composition compoFindFill = new Composition(compoFind.getId(), compoFind.getCreatedDate(),
				compoFind.getPlaysNumber(), compoFind.getOwner(), compoFind.getCollaborations());
		compoFindFill.setTracks(compoFind.getTracks());
		assertEquals(100, compoFindFill.getTracks().get(0).getDuration());
	}

	@Test
	public void compositionDaoCollabRemove() {
		Composer composer = new Composer();
		composer.setLastname("Jean");
		composer.setFirstname("Dupont");
		composer.setCountry("France");
		composer.setEmail("jeandupont@mail.fr");
		composer.setSubscribedDate(new Date());

		composer = composerDao.save(composer);

		Composition compo1 = new Composition();
		compo1.setCreatedDate(new Date());
		compo1.setPlaysNumber(50);
		compo1.setOwner(composer);

		compo1 = compositionDao.save(compo1);

		Collaboration collab = new Collaboration(new Date(), null, null, compo1, composer, Status.WAITING,
				Right.READONLY);
		collab = collaborationDao.save(collab);

		compo1.addCollaboration(collab);
		compo1.add(new Track());
		compositionDao.save(compo1);

		Composition compoSaved = compositionDao.findById(compo1.getId()).get();
		compoSaved.removeCollaboration(0);
		compoSaved.remove(0);

		assertEquals(0, compoSaved.getCollaborations().size());
		assertEquals(0, compoSaved.getTracks().size());
	}

}
