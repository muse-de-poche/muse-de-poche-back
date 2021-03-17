package org.musedepoche.dao;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composer;
import org.musedepoche.model.Composition;
import org.musedepoche.model.Right;
import org.musedepoche.model.Status;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class DataDao {

	@Autowired
	protected IComposerDao composerDao;

	@Autowired
	protected ICompositionDao compositionDao;

	@Autowired
	protected ICollaborationDao collaborationDao;

	@Autowired
	protected IMessageDao messageDao;

	@Autowired
	protected IMetronomeDao metronomeDao;

	@Autowired
	protected ITrackDao trackDao;

	@Autowired
	protected ISoundDao soundDao;
	
	protected int numberOfComposer;
	protected int numberOfComposition;
	protected int numberOfCollaboration;
	protected int numberOfMessage;
	protected int numberOfMetronome;
	protected int numberOfTrask;
	protected int numberOfSound;

	@BeforeAll
	public void init() {
		/* Composers */
		Composer jean = new Composer("Jmarc","Jmarc123","Jean", "Marc", "France", "jean.marc@gmail.com", new Date());
		Composer bea = new Composer("Btyne","Btyne123","Bea", "Tyne", "France", "bea.tyne@gmail.com", new Date());
		Composer jc = new Composer("Jvd","Jvd123","jc", "vd", "France", "jc.vd@gmail.com", new Date());
		Composer axel = new Composer("Atune","Atune123","Axel", "Tune", "France", "axel.tune@gmail.com", new Date());
		Composer celine = new Composer("Cpierre","Cpierre123","Celine", "Pierre", "France", "celine.pierre@gmail.com", new Date());
		Composer marc = new Composer("Mjean","Mjean123","Marc", "Jean", "France", "marc.jean@gmail.com", new Date());

		jean = composerDao.save(jean);
		bea = composerDao.save(bea);
		jc = composerDao.save(jc);
		axel = composerDao.save(axel);
		celine = composerDao.save(celine);
		marc = composerDao.save(marc);
		
		this.numberOfComposer = 6;

		/* Compositions */
		Composition cp1 = new Composition(new Date(), 1002, jean);
		Composition cp2 = new Composition(new Date(), 658495, bea);
		Composition cp3 = new Composition(new Date(), 10, jean);
		Composition cp4 = new Composition(new Date(), 536, axel);
		Composition cp5 = new Composition(new Date(), 485, celine);
		Composition cp6 = new Composition(new Date(), 1002, marc);
		Composition cp7 = new Composition(new Date(), 1, jc);

		cp1 = compositionDao.save(cp1);
		cp2 = compositionDao.save(cp2);
		cp3 = compositionDao.save(cp3);
		cp4 = compositionDao.save(cp4);
		cp5 = compositionDao.save(cp5);
		cp6 = compositionDao.save(cp6);
		cp7 = compositionDao.save(cp7);

		this.numberOfComposition = 7;
		
		/* Collaborations */
		Collaboration col1 = new Collaboration(new Date(), new Date(), new Date(), cp1, bea, Status.ACCEPTED,
				Right.READONLY);
		Collaboration col2 = new Collaboration(new Date(), new Date(), new Date(), cp1, axel, Status.ACCEPTED,
				Right.READONLY);
		Collaboration col3 = new Collaboration(new Date(), new Date(), new Date(), cp2, marc, Status.REJECTED,
				Right.READONLY);
		Collaboration col4 = new Collaboration(new Date(), new Date(), new Date(), cp2, celine, Status.ACCEPTED,
				Right.FULL);
		Collaboration col5 = new Collaboration(new Date(), new Date(), new Date(), cp2, jean, Status.ACCEPTED,
				Right.WRITE);
		Collaboration col6 = new Collaboration(new Date(), new Date(), new Date(), cp3, bea, Status.ACCEPTED,
				Right.FULL);
		Collaboration col7 = new Collaboration(new Date(), new Date(), new Date(), cp3, marc, Status.BANNED,
				Right.READONLY);
		Collaboration col8 = new Collaboration(new Date(), new Date(), new Date(), cp4, bea, Status.ACCEPTED,
				Right.WRITE);
		Collaboration col9 = new Collaboration(new Date(), new Date(), new Date(), cp5, bea, Status.ACCEPTED,
				Right.FULL);
		Collaboration col10 = new Collaboration(new Date(), new Date(), new Date(), cp5, jean, Status.BANNED,
				Right.READONLY);
		Collaboration col11 = new Collaboration(new Date(), new Date(), new Date(), cp5, axel, Status.CANCELED,
				Right.READONLY);
		Collaboration col12 = new Collaboration(new Date(), new Date(), new Date(), cp6, bea, Status.ACCEPTED,
				Right.READONLY);
		Collaboration col13 = new Collaboration(new Date(), new Date(), new Date(), cp7, jean, Status.ACCEPTED,
				Right.READONLY);

		col1 = collaborationDao.save(col1);
		col2 = collaborationDao.save(col2);
		col3 = collaborationDao.save(col3);
		col4 = collaborationDao.save(col4);
		col5 = collaborationDao.save(col5);
		col6 = collaborationDao.save(col6);
		col7 = collaborationDao.save(col7);
		col8 = collaborationDao.save(col8);
		col9 = collaborationDao.save(col9);
		col10 = collaborationDao.save(col10);
		col11 = collaborationDao.save(col11);
		col12 = collaborationDao.save(col12);
		col13 = collaborationDao.save(col13);

		this.numberOfCollaboration = 13;
		
		/* list composition by composer */
		List<Composition> jeanCompositions = List.of(cp1, cp3);
		List<Composition> beaCompositions = List.of(cp2);
		List<Composition> jcCompositions = List.of(cp7);
		List<Composition> axelCompositions = List.of(cp4);
		List<Composition> celineCompositions = List.of(cp5);
		List<Composition> marcCompositions = List.of(cp6);

		/* list collaboration by composer */
		List<Collaboration> jeanCollaboration = List.of(col5, col10, col13);
		List<Collaboration> beaCollaboration = List.of(col1, col6, col8, col9, col12);
		List<Collaboration> jcCollaboration = List.of(

		);
		List<Collaboration> axelCollaboration = List.of(col2, col11);
		List<Collaboration> celineCollaboration = List.of(col4);
		List<Collaboration> marcCollaboration = List.of(col3, col7);

		/* list collaboration by composition */
		List<Collaboration> cp1Collaboration = List.of(col1, col2);
		List<Collaboration> cp2Collaboration = List.of(col3, col4, col5);
		List<Collaboration> cp3Collaboration = List.of(col6, col7);
		List<Collaboration> cp4Collaboration = List.of(col8);
		List<Collaboration> cp5Collaboration = List.of(col9, col10, col11);
		List<Collaboration> cp6Collaboration = List.of(col12);
		List<Collaboration> cp7Collaboration = List.of(col13);

		/* update composer with list<composition> and list<collaboration> */
		jean.setCompositions(jeanCompositions);
		jean.setCollaborations(jeanCollaboration);
		composerDao.save(jean);

		bea.setCompositions(beaCompositions);
		bea.setCollaborations(beaCollaboration);
		composerDao.save(bea);

		jc.setCompositions(jcCompositions);
		jc.setCollaborations(jcCollaboration);
		composerDao.save(jc);

		axel.setCompositions(axelCompositions);
		axel.setCollaborations(axelCollaboration);
		composerDao.save(axel);

		celine.setCompositions(celineCompositions);
		celine.setCollaborations(celineCollaboration);
		composerDao.save(celine);

		marc.setCompositions(marcCompositions);
		marc.setCollaborations(marcCollaboration);
		composerDao.save(marc);

		/* update composition with list<collaboration> */
		cp1.setCollaborations(cp1Collaboration);
		compositionDao.save(cp1);

		cp2.setCollaborations(cp2Collaboration);
		compositionDao.save(cp2);

		cp3.setCollaborations(cp3Collaboration);
		compositionDao.save(cp3);

		cp4.setCollaborations(cp4Collaboration);
		compositionDao.save(cp4);

		cp5.setCollaborations(cp5Collaboration);
		compositionDao.save(cp5);

		cp6.setCollaborations(cp6Collaboration);
		compositionDao.save(cp6);

		cp7.setCollaborations(cp7Collaboration);
		compositionDao.save(cp7);
	}

}
