// Compilation : javac -sourcepath ./srctest -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/*.java
// Exécution : java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.TestMonde
package fr.insarouen.asi.prog.asiaventure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.core.IsEqual;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;

public class TestMonde {
	public Monde monde;
	public Entite ent;

	@Before
	public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.monde = new Monde("test");
		this.ent = new Entite("Entite", monde){};
	}

	@Test
	public void testAjouter() {
		assertThat(this.ent, IsEqual.equalTo(this.monde.getEntite("Entite")));
	}

	@Test(expected=NomDEntiteDejaUtiliseDansLeMondeException.class)
	public void testAjouterEntiteEnDouble() throws NomDEntiteDejaUtiliseDansLeMondeException {
		Entite entiteEnTrop = new Entite("Entite", monde){};
	}
}