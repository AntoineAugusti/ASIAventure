// Compilation : javac -sourcepath ./srctest -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/elements/objets/TestObjet.java
// Exécution : java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.elements.objets.TestObjet
package fr.insarouen.asi.prog.asiaventure.elements.objets;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;

import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.ObjetNonPossedeParLeVivantException;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;

public class TestObjet {
	public Monde monde;
	public Objet objDeplacable;
	public Objet objNonDeplacable;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("test");
		this.objDeplacable = new Objet("Deplacable", monde) {
			public boolean estDeplacable() {
				return true;
			}
		};
		this.objNonDeplacable = new Objet("NonDeplacable", monde) {
			public boolean estDeplacable() {
				return false;
			}
		};
	}

	@Test
	public void testConstructeur() {
		assertThat(this.monde.equals(this.objDeplacable.getMonde()), is(true));
		assertThat(this.objDeplacable.getNom().equals("Deplacable"), is(true));
	}

	@Test
	public void testDeplacable() throws ObjetNonPossedeParLeVivantException {
		assertThat(this.objDeplacable.estDeplacable(), is(true));
		assertThat(this.objNonDeplacable.estDeplacable(), is(false));
	}
}