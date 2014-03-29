// Compilation : javac -sourcepath ./srctest -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/elements/*.java
// Exécution : java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.elements.TestEntite
package fr.insarouen.asi.prog.asiaventure.elements;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.core.IsEqual;
import static org.hamcrest.core.Is.is;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;

public class TestEntite {
	public Monde monde;
	public Entite ent;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("test");
		this.ent = new Entite("Entite", monde){};
	}

	@Test
	public void testConstructeur() {
		assertThat(this.monde, IsEqual.equalTo(this.ent.getMonde()));
		assertThat(this.ent.getNom().equals("Entite"), is(true));
	}

	@Test
	public void testEquals() throws NomDEntiteDejaUtiliseDansLeMondeException {
		Entite entiteDeux = new Entite("EntiteDeux", monde){};
		Entite entiteAutreMonde = new Entite("Entite", new Monde("autreMonde")){};

		assertThat(this.ent.equals(entiteDeux), is(false));
		assertThat(this.ent.equals(entiteAutreMonde), is(false));
	}
}