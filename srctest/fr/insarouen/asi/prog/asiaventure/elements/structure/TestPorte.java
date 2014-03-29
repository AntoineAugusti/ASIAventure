// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/vivants/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
package fr.insarouen.asi.prog.asiaventure.elements.structure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;

import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Clef;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;

import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;


public class TestPorte {
	public Porte porte;
	public Monde monde;
	public Vivant vivant;
	public Piece pieceA;
	public Piece pieceB;
	public PiedDeBiche pdb;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("test");
		this.pdb = new PiedDeBiche("PdB", monde);
		this.pieceA = new Piece("SdB", monde);
		this.pieceB = new Piece("SdB2", monde);
		this.porte = new Porte("Porte", monde, pieceA, pieceB);
	}

	@Test
	public void testConstructeur() {
		assertThat(this.porte.getEtat().equals(Etat.FERME), is(true));
	}

	@Test
	public void testPieceAutreCote() {
		assertThat(this.porte.getPieceAutreCote(this.pieceA).equals(pieceB), is(true));
		assertThat(this.porte.getPieceAutreCote(this.pieceB).equals(pieceA), is(true));
	}

	@Test
	public void testActivation() throws ActivationImpossibleException {
		this.porte.activer();
		assertThat(this.porte.getEtat().equals(Etat.OUVERT), is(true));
		this.porte.activer();
		assertThat(this.porte.getEtat().equals(Etat.FERME), is(true));
	}

	@Test
	public void testActivationAvecPDB() throws ActivationImpossibleException {
		assertThat(this.porte.activableAvec(pdb), is(true));
		this.porte.activerAvec(pdb);
		assertThat(this.porte.getEtat().equals(Etat.CASSE), is(true));
	}

	@Test(expected=ActivationImpossibleException.class)
	public void testActivationAvecPDBSurPorteCassee() throws ActivationImpossibleException {
		this.porte.activerAvec(pdb);
		this.porte.activer();
	}
}