// Compilation : javac -sourcepath ./srctest:./src -cp ./classes:/usr/share/java/junit4-4.11.jar -d ./classestest ./srctest/fr/insarouen/asi/prog/asiaventure/*.java
// Exécution : java -cp ./classes:./classestest:/usr/share/java/junit4-4.11.jar org.junit.runner.JUnitCore fr.insarouen.asi.prog.asiaventure.SuiteASIAventure
package fr.insarouen.asi.prog.asiaventure;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.insarouen.asi.prog.asiaventure.elements.SuiteElements;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestMonde.class,
	TestSimulateur.class,
	SuiteElements.class
})
public class SuiteASIAventure {}