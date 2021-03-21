package org.musedepoche.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.musedepoche.model.Collaboration;
import org.musedepoche.model.Composer;
import org.musedepoche.model.Composition;
import org.musedepoche.model.Metronome;
import org.musedepoche.model.Right;
import org.musedepoche.model.Sound;
import org.musedepoche.model.Status;
import org.musedepoche.model.Track;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mutualisation du mock pour les *Jpa repository.
 * 
 * @author Martin L.
 * @author Cyril R.
 */
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
	protected List<Composer> composers = new ArrayList();
	protected List<Collaboration> collaborations;
	protected List<Composition> compositions;	

	@BeforeAll
	public void init() {
		/* Composers */
		Composer jean = new Composer("Jmarc", "Jmarc123", "Jean", "Marc", "France", "jean.marc@gmail.com", new Date());
		Composer bea = new Composer("Btyne", "Btyne123", "Bea", "Tyne", "France", "bea.tyne@gmail.com", new Date());
		Composer jc = new Composer("Jvd", "Jvd123", "jc", "vd", "France", "jc.vd@gmail.com", new Date());
		Composer axel = new Composer("Atune", "Atune123", "Axel", "Tune", "France", "axel.tune@gmail.com", new Date());
		Composer celine = new Composer("Cpierre", "Cpierre123", "Celine", "Pierre", "France", "celine.pierre@gmail.com",
				new Date());
		Composer marc = new Composer("Mjean", "Mjean123", "Marc", "Jean", "France", "marc.jean@gmail.com", new Date());

		jean = composerDao.save(jean);
		bea = composerDao.save(bea);
		jc = composerDao.save(jc);
		axel = composerDao.save(axel);
		celine = composerDao.save(celine);
		marc = composerDao.save(marc);

		this.numberOfComposer = 6;

		/* Compositions */

		Composition cp1 = new Composition("compo1", 1002, jean);
		Composition cp2 = new Composition("compo2", 658495, bea);
		Composition cp3 = new Composition("compo3", 10, jean);
		Composition cp4 = new Composition("compo4", 536, axel);
		Composition cp5 = new Composition("compo5", 485, celine);
		Composition cp6 = new Composition("compo6", 1002, marc);
		Composition cp7 = new Composition("compo7", 1, jc);

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
				Right.READONLY, "Lorem ipsum dolor sit amet, consectetur efficitur.");
		Collaboration col2 = new Collaboration(new Date(), new Date(), new Date(), cp1, axel, Status.ACCEPTED,
				Right.READONLY,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra sed nisi nec dapibus. Duis et nulla id.");
		Collaboration col3 = new Collaboration(new Date(), new Date(), new Date(), cp2, marc, Status.REJECTED,
				Right.READONLY, "Nunc elementum fringilla tortor vitae luctus efficitur.");
		Collaboration col4 = new Collaboration(new Date(), new Date(), new Date(), cp2, celine, Status.ACCEPTED,
				Right.FULL, "Fusce bibendum neque a semper maximus. Nam turpis duis.");
		Collaboration col5 = new Collaboration(new Date(), new Date(), new Date(), cp2, jean, Status.ACCEPTED,
				Right.WRITE, "Etiam et viverra tellus. Cras quis aenean.");
		Collaboration col6 = new Collaboration(new Date(), new Date(), new Date(), cp3, bea, Status.ACCEPTED,
				Right.FULL, "Pellentesque ut erat porttitor.");
		Collaboration col7 = new Collaboration(new Date(), new Date(), new Date(), cp3, marc, Status.BANNED,
				Right.READONLY,
				"Vivamus dignissim lacinia euismod. Donec venenatis magna felis, id cursus mi bibendum a. Quisque ultrices est non.");
		Collaboration col8 = new Collaboration(new Date(), new Date(), new Date(), cp4, bea, Status.ACCEPTED,
				Right.WRITE, "CCTVVMB");
		Collaboration col9 = new Collaboration(new Date(), new Date(), new Date(), cp5, bea, Status.ACCEPTED,
				Right.FULL, "Vestibulum sodales, sem sit amet imperdiet commodo, magna tortor faucibus arcu posuere.");
		Collaboration col10 = new Collaboration(new Date(), new Date(), new Date(), cp5, jean, Status.BANNED,
				Right.READONLY, "Nullam ut euismod tortor. Integer tincidunt.");
		Collaboration col11 = new Collaboration(new Date(), new Date(), new Date(), cp5, axel, Status.CANCELED,
				Right.READONLY, "Nullam faucibus quis tellus et viverra.");
		Collaboration col12 = new Collaboration(new Date(), new Date(), new Date(), cp6, bea, Status.ACCEPTED,
				Right.READONLY, "Ut purus lorem, interdum ac congue a, ultricies eu mauris.");
		Collaboration col13 = new Collaboration(new Date(), new Date(), new Date(), cp7, jean, Status.ACCEPTED,
				Right.READONLY, "Morbi nec velit placerat, rhoncus ut.");

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
		
		Metronome met1 = new Metronome("4/4", 1, 120);
		Metronome met2 = new Metronome("5/4", 3, 90);
		Metronome met3 = new Metronome("6/8", 2, 160);
		Metronome met4 = new Metronome("12/8", 1, 100);

		met1 = metronomeDao.save(met1);
		met2 = metronomeDao.save(met2);
		met3 = metronomeDao.save(met3);
		met4 = metronomeDao.save(met4);
		
		cp1.setMetronome(met1);
		cp2.setMetronome(met2);
		cp5.setMetronome(met3);
		cp7.setMetronome(met4);
		
		Track tr1 = new Track(0, "guitare", null, cp1);
		Track tr2 = new Track(0, "basse", null, cp1);
		Track tr3 = new Track(0, "voix lead", null, cp1);
		Track tr4 = new Track(0, "choeurs", null, cp1);
		Track tr5 = new Track(0, "guitare folk", null, cp2);
		Track tr6 = new Track(0, "triangle", null, cp2);
		Track tr7 = new Track(0, "guimbarde", null, cp3);
		Track tr8 = new Track(0, "ukulélé", null, cp5);
		Track tr9 = new Track(0, "aisselle", null, cp5);

		tr1 = trackDao.save(tr1);
		tr2 = trackDao.save(tr2);
		tr3 = trackDao.save(tr3);
		tr4 = trackDao.save(tr4);
		tr5 = trackDao.save(tr5);
		tr6 = trackDao.save(tr6);
		tr7 = trackDao.save(tr7);
		tr8 = trackDao.save(tr8);
		tr9 = trackDao.save(tr9);

		/* update composer with list<composition> and list<collaboration> */
		jean.setCompositions(jeanCompositions);
		jean.setCollaborations(jeanCollaboration);
		jean = composerDao.save(jean);

		bea.setCompositions(beaCompositions);
		bea.setCollaborations(beaCollaboration);
		bea = composerDao.save(bea);

		jc.setCompositions(jcCompositions);
		jc.setCollaborations(jcCollaboration);
		jc = composerDao.save(jc);

		axel.setCompositions(axelCompositions);
		axel.setCollaborations(axelCollaboration);
		axel = composerDao.save(axel);

		celine.setCompositions(celineCompositions);
		celine.setCollaborations(celineCollaboration);
		celine = composerDao.save(celine);

		marc.setCompositions(marcCompositions);
		marc.setCollaborations(marcCollaboration);
		marc = composerDao.save(marc);

		/* update composition with list<collaboration> */
		cp1.setCollaborations(cp1Collaboration);
		cp1 = compositionDao.save(cp1);

		cp2.setCollaborations(cp2Collaboration);
		cp2 = compositionDao.save(cp2);

		cp3.setCollaborations(cp3Collaboration);
		cp3 = compositionDao.save(cp3);

		cp4.setCollaborations(cp4Collaboration);
		cp4 = compositionDao.save(cp4);

		cp5.setCollaborations(cp5Collaboration);
		cp5 = compositionDao.save(cp5);

		cp6.setCollaborations(cp6Collaboration);
		cp6 = compositionDao.save(cp6);

		cp7.setCollaborations(cp7Collaboration);
		cp7 = compositionDao.save(cp7);
		
		Path path = Paths.get("src/test/resources/sample.wav");
		Sound sound = null;
		try {
			sound = new Sound(0,Files.readAllBytes(path), tr1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sound = soundDao.save(sound);

		
		//this.composers = List.of(jean,bea,jc,axel,celine,marc);
		composers.addAll(List.of(jean,bea,jc,axel,celine,marc));
		this.collaborations = List.of(col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13);
		this.compositions = List.of(cp1,cp2,cp3,cp4,cp5,cp6,cp7);
		
	}

}
