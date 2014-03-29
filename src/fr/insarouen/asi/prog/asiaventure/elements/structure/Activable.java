package fr.insarouen.asi.prog.asiaventure.elements.structure;

import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;

public interface Activable {
	boolean activableAvec(Objet obj);
	void activer() throws ActivationException;
	void activerAvec(Objet obj) throws ActivationException;
	Etat getEtat();
}