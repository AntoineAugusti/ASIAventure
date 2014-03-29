// Compilation : javac -sourcepath ./srctest -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/elements/vivants/TestVivant.java
// Exécution : java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.elements.vivants.TestVivant
package fr.insarouen.asi.prog.asiaventure.elements.vivants;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;

import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;
import fr.insarouen.asi.prog.asiaventure.ASIAventureException;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetNonDeplacableDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.ObjetNonPossedeParLeVivantException;

public class TestVivant {
	public Monde monde;
	public Vivant vivant;
	public PiedDeBiche pdb;
	public Piece piece;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("test");
		this.pdb = new PiedDeBiche("PdB", monde);
		this.piece = new Piece("SdB", monde);
		this.vivant = new Vivant("TestVivant", monde, 5, 10, piece, pdb){
			public void executer(){

			}
		};
	}

	@Test
	public void testConstructeur() {
		assertThat(this.piece, equalTo(this.vivant.getPiece()));
		assertThat(5, equalTo(this.vivant.getPointVie()));
		assertThat(10, equalTo(this.vivant.getPointForce()));
		assertThat(this.vivant.estMort(), is(false));
		assertThat(this.vivant.possede(this.pdb), is(true));
		assertThat(this.vivant.getObjet("PdB").equals(this.pdb), is(true));
	}

	@Test
	public void testDeposer() throws ObjetNonPossedeParLeVivantException {
		this.vivant.deposer(this.pdb);
		assertThat(this.vivant.possede(this.pdb), is(false));
	}

	@Test
	public void testPrendre() throws ObjetNonPossedeParLeVivantException,ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		this.vivant.deposer(this.pdb);
		this.vivant.prendre(this.pdb);
		assertThat(this.vivant.possede(this.pdb), is(true));
		this.vivant.deposer(this.pdb);
		assertThat(this.vivant.possede(this.pdb), is(false));
		this.vivant.prendre("PdB");
		assertThat(this.vivant.possede(this.pdb), is(true));
	}
}