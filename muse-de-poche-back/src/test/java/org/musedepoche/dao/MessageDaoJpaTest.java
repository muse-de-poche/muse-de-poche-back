package org.musedepoche.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.musedepoche.model.Message;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageDaoJpaTest extends DataDao {

	@Test
	public void basicCRUDTest() {
		Message original = new Message(new Date(), this.composerDao.findById(1L).get(), this.compositionDao.findById(1L).get(), new String("Hey !"));
		Message saved, updated;
		
		// Create Test
		saved = this.messageDao.save(original);
		
		assertNotNull(saved);
		assertFalse(saved.getSendingDate().equals(original.getSendingDate()));
		assertFalse(saved.getSender().getId() == original.getSender().getId());
		assertFalse(saved.getSubject().getId() == original.getSubject().getId());
		assertFalse(saved.getText().equals(original.getText()));
		
		// Update Test
		saved.setText(new String("HOHO !"));
		
		updated = this.messageDao.save(saved);
		
		assertFalse(updated.getText().equals(saved.getText()));
		
		// Re-read test
		
		// Delete test
	}
	
	@Test
	public void messageFindByComposer() {
//		// Une composition éxistante
//		List<Message> collaborations = this.messageDao.findByComposition(1L);
//		int lenghtOfCollaborations = 1;
//
//		assertFalse(collaborations.isEmpty());
//		assertTrue(collaborations.size() == lenghtOfCollaborations);// TODO: definir lenghtOfCollaborations
//
//		// Non existante
//		collaborations = this.collaborationDao.findByComposition(666L);
//		lenghtOfCollaborations = 1;
//
//		assertTrue(collaborations.isEmpty());
//		assertTrue(collaborations.size() == lenghtOfCollaborations);
	}
	
	@Test
	public void messageFindByCompositeur() {
//		// Une composition éxistante
//		List<Message> collaborations = this.messageDao.findByComposition(1L);
//		int lenghtOfCollaborations = 1;
//
//		assertFalse(collaborations.isEmpty());
//		assertTrue(collaborations.size() == lenghtOfCollaborations);// TODO: definir lenghtOfCollaborations
//
//		// Non existante
//		collaborations = this.collaborationDao.findByComposition(666L);
//		lenghtOfCollaborations = 1;
//
//		assertTrue(collaborations.isEmpty());
//		assertTrue(collaborations.size() == lenghtOfCollaborations);
	}

}
