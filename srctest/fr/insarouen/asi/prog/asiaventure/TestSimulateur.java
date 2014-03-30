package fr.insarouen.asi.prog.asiaventure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.core.IsEqual;

import java.io.StreamTokenizer;
import java.io.Reader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileReader;

import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Clef;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Porte;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.JoueurHumain;

public class TestSimulateur {
	public Monde monde;
	public Piece piece1;
	public Piece piece2;
	public Piece piece3;
	public Piece piece4;
	public Piece piece5;
	public Porte porteSerrure1;
	public Porte porteSerrure2;
	public Porte porte1;
	public Porte porte2;
	public Porte porte3;
	public Porte porte4;
	public Clef cle1;
	public Clef cle2;
	public JoueurHumain etudiant;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("LeMondeimpitoyabledASI");
		this.piece1 =  new Piece("BureauDesNicolas", this.monde);
		this.piece2 =  new Piece("BureauDuDirecteur", this.monde);
		this.piece3 =  new Piece("BureauDeRakoto", this.monde);
		this.piece4 =  new Piece("BureauDeGasso", this.monde);
		this.piece5 =  new Piece("Couloir", this.monde);
		this.porteSerrure1 = new Porte("Porte1", new Serrure(this.monde), this.monde, this.piece5, this.piece1);
		this.porteSerrure2 = new Porte("Porte2", new Serrure(this.monde), this.monde, this.piece5, this.piece2);
		this.porte1 = new Porte("Porte3", this.monde, this.piece5, this.piece3);
		this.porte2 = new Porte("Porte4", this.monde, this.piece5, this.piece4);
		this.porte3 = new Porte("Trappe", this.monde, this.piece5, this.piece2);
		this.porte4 = new Porte("PassageSecret", this.monde, this.piece5, this.piece4);
		this.cle1 = porteSerrure1.getSerrure().creerClef();
		this.piece3.deposer(this.cle1);
		this.cle2 = porteSerrure2.getSerrure().creerClef();
		this.piece4.deposer(this.cle2);
		this.etudiant = new JoueurHumain("Etudiant1", this.monde, 10, 10, this.piece5);
	}

	@Test
	public void testFichierChargement() throws Throwable {
		Simulateur sim = new Simulateur(new FileReader("srctest/fr/insarouen/asi/prog/asiaventure/chargement.txt"));
	}
}