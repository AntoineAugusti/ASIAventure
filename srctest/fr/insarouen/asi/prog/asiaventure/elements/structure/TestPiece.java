// Compilation : javac -sourcepath ./srctest -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/elements/structure/TestPiece.java
// Exécution : java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.elements.structure.TestPiece
package fr.insarouen.asi.prog.asiaventure.elements.structure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;


import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.VivantAbsentDeLaPieceException;

public class TestPiece {
	public Monde monde;
	public Vivant vivant;
	public Piece piece;
	public PiedDeBiche pdb;

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
	public void testDeposer() {
		assertThat(this.piece.contientObjet(pdb), is(false));
		assertThat(this.piece.contientObjet("PdB"), is(false));
		this.piece.deposer(pdb);
		assertThat(this.piece.contientObjet(pdb), is(true));
		assertThat(this.piece.contientObjet("PdB"), is(true));
	}

	@Test
	public void testRetirer() throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		this.piece.deposer(pdb);
		assertThat(this.piece.contientObjet(pdb), is(true));
		assertThat(this.piece.contientObjet("PdB"), is(true));
		this.piece.retirer(pdb);
		assertThat(this.piece.contientObjet(pdb), is(false));
		
		this.piece.deposer(pdb);
		this.piece.retirer("PdB");
		assertThat(this.piece.contientObjet(pdb), is(false));
	}

	@Test
	public void testEntrerEtSortir() throws VivantAbsentDeLaPieceException {
		this.piece.sortir(vivant);
		assertThat(this.piece.contientVivant(vivant), is(false));
		assertThat(this.piece.contientVivant("TestVivant"), is(false));

		this.piece.entrer(vivant);
		assertThat(this.piece.contientVivant(vivant), is(true));
		assertThat(this.piece.contientVivant("TestVivant"), is(true));
	}

	@Test(expected=VivantAbsentDeLaPieceException.class)
	public void sortirFaux() throws VivantAbsentDeLaPieceException, NomDEntiteDejaUtiliseDansLeMondeException {
		Piece pieceTemp = new Piece("Salon", this.monde);
		Vivant vivantTemp = new Vivant("TestVivantTemp", this.monde, 5, 10, pieceTemp, pdb){
			public void executer(){

			}
		};
		
		this.piece.sortir(vivantTemp);
	}
}