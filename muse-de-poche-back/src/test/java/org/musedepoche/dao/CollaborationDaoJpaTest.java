package org.musedepoche.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composition;
import org.musedepoche.model.Status;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CollaborationDaoJpaTest extends DataDao {
	
	@Test
	public void basicCRUDTest() {
		Composition original = new Composition(new Date(), 10, super.composerDao.findById(1L).get(), null);
		Composition saved, updated;
		
		// Create Test
		saved = super.compositionDao.save(original);
		
		assertNotNull(saved);
		assertEquals(saved.getOwner().getId(), original.getOwner().getId());
		assertEquals(saved.getCreatedDate(), original.getCreatedDate());
		assertEquals(saved.getPlaysNumber(), original.getPlaysNumber());
		
		// Update Test
		saved.setPlaysNumber(666);
		
		updated = super.compositionDao.save(saved);
		
		assertEquals(updated.getPlaysNumber(), saved.getPlaysNumber());
		
		// Read test
		original = super.compositionDao.findById(updated.getId()).get();
		
		assertEquals(original.getId(), updated.getId());
		
		// Delete test
		super.compositionDao.delete(updated);
		
		assertTrue(super.compositionDao.findById(original.getId()).isEmpty());
	}
	
	@Test
	public void collaborationFindByComposition() {
		// Une composition éxistante
		List<Collaboration> collaborations = super.collaborationDao.findByComposition(1L);
		int lenghtOfCollaborations = 1;

		assertFalse(collaborations.isEmpty());
		assertTrue(collaborations.size() == lenghtOfCollaborations);// TODO: definir lenghtOfCollaborations

		// Non existante
		collaborations = super.collaborationDao.findByComposition(666L);
		lenghtOfCollaborations = 1;

		assertTrue(collaborations.isEmpty());
		assertTrue(collaborations.size() == lenghtOfCollaborations);
	}

	@Test
	public void collaborationFindByComposer() {
		// Un compositeur existant
		List<Collaboration> collaborations = super.collaborationDao.findByComposer(1L);
		int lenghtOfCollaborations = 0;

		assertFalse(collaborations.isEmpty());
		assertTrue(collaborations.size() == lenghtOfCollaborations);// TODO: definir lenghtOfCollaborations

		// Non existant
		collaborations = super.collaborationDao.findByComposition(666L);
		lenghtOfCollaborations = 0;

		assertTrue(collaborations.isEmpty());
		assertTrue(collaborations.size() == lenghtOfCollaborations);
	}

	@Test
	public void collaborationFindByStatus() {
		List<Collaboration> collaborations = super.collaborationDao.findByStatus(Status.WAITING);
		int lenghtOfCollaborations = 0;

		assertFalse(collaborations.isEmpty());
		assertTrue(collaborations.size() == lenghtOfCollaborations);// TODO: definir lenghtOfCollaborations

		// Non existant
		collaborations = super.collaborationDao.findByStatus(Status.REVOKED);
		lenghtOfCollaborations = 0;

		assertTrue(collaborations.isEmpty());
		assertTrue(collaborations.size() == lenghtOfCollaborations);
	}

}
