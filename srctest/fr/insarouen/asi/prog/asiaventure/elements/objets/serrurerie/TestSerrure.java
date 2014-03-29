// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/serrurerie/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
package fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.CoreMatchers.instanceOf;

import fr.insarouen.asi.prog.asiaventure.elements.structure.Activable;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationImpossibleException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationImpossibleAvecObjetException;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import java.util.Random;

import fr.insarouen.asi.prog.asiaventure.elements.structure.Porte;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;

public class TestSerrure {
	public Porte porte;
	public Porte porteVerrouillee;
	public Monde monde;
	public Vivant vivant;
	public Piece pieceA;
	public Piece pieceB;
	public PiedDeBiche pdb;
	public Serrure serrure;
	public Serrure serrure2;
	public Clef clefSerrure;
	public Clef clefSerrure2;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("test");
		this.pdb = new PiedDeBiche("PdB", monde);
		this.pieceA = new Piece("SdB", monde);
		this.pieceB = new Piece("SdB2", monde);
		this.porte = new Porte("Porte", monde, pieceA, pieceB);
		this.serrure = new Serrure("Serrure", monde);
		this.serrure2 = new Serrure(monde);
		this.porteVerrouillee = new Porte("porteVerrouillee", serrure, monde, pieceA, pieceB);
		this.clefSerrure = this.serrure.creerClef();
		this.clefSerrure2 = this.serrure2.creerClef();
	}

	@Test
	public void testConstructeur() {
		assertThat(this.serrure.getEtat().equals(Etat.VERROUILLE), is(true));
	}

	@Test
	public void testCreationCle() throws NomDEntiteDejaUtiliseDansLeMondeException {
		assertThat(clefSerrure, instanceOf(Clef.class));
		assertThat(this.serrure.creerClef(), is(nullValue()));
	}

	@Test
	public void testSerrureActivableAvecBonneCle() {
		assertThat(this.serrure.activableAvec(clefSerrure), is(true));
		assertThat(this.serrure.activableAvec(clefSerrure2), is(false));
		assertThat(this.serrure2.activableAvec(clefSerrure2), is(true));
	}

	@Test
	public void testSerrureActivableAvecPDB() {
		assertThat(this.serrure.activableAvec(pdb), is(true));
		assertThat(this.serrure2.activableAvec(pdb), is(true));
	}

	@Test(expected=ActivationImpossibleException.class)
	public void testSerrureActiverSansObjet() throws ActivationImpossibleException {
		this.serrure.activer();
	}

	@Test
	public void testActiverAvecClefs() throws ActivationImpossibleAvecObjetException {
		this.serrure.activerAvec(clefSerrure);
		assertThat(this.serrure.getEtat().equals(Etat.DEVERROUILLE), is(true));
		this.serrure.activerAvec(clefSerrure);
		assertThat(this.serrure.getEtat().equals(Etat.VERROUILLE), is(true));
	}

	@Test
	public void testActiverAvecPDB() throws ActivationImpossibleAvecObjetException {
		this.serrure.activerAvec(pdb);
		assertThat(this.serrure.getEtat().equals(Etat.CASSE), is(true));
	}

	@Test
	public void testPorteVerrouille() {
		assertThat(this.porteVerrouillee.getEtat().equals(Etat.VERROUILLE), is(true));
	}

	@Test
	public void testPorteVerrouilleActivable() {
		assertThat(this.porteVerrouillee.activableAvec(clefSerrure), is(true));
		assertThat(this.porteVerrouillee.activableAvec(pdb), is(true));
		assertThat(this.porteVerrouillee.activableAvec(clefSerrure2), is(false));
	}

	@Test(expected=ActivationImpossibleException.class)
	public void testPorteVerrouilleeActiverSansObjet() throws ActivationImpossibleException {
		this.porteVerrouillee.activer();
	}

	@Test
	public void testPorteVerrouilleActiverAvecCleDeSerrure() throws ActivationImpossibleAvecObjetException, ActivationImpossibleException {
		// Déverrouillage de la porte
		this.porteVerrouillee.activerAvec(clefSerrure);
		assertThat(this.porteVerrouillee.getEtat().equals(Etat.OUVERT), is(true));
		assertThat(this.serrure.getEtat().equals(Etat.DEVERROUILLE), is(true));
		
		// Verrouillage de la porte
		this.porteVerrouillee.activerAvec(clefSerrure);
		assertThat(this.porteVerrouillee.getEtat().equals(Etat.VERROUILLE), is(true));
		assertThat(this.serrure.getEtat().equals(Etat.VERROUILLE), is(true));
	}

	@Test
	public void testPorteVerrouilleActiverAvecPDB() throws ActivationImpossibleAvecObjetException, ActivationImpossibleException {
		// Enfonçage de la porte
		this.porteVerrouillee.activerAvec(pdb);
		assertThat(this.porteVerrouillee.getEtat().equals(Etat.CASSE), is(true));
		assertThat(this.serrure.getEtat().equals(Etat.CASSE), is(true));
	}

}