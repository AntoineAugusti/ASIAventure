package fr.insarouen.asi.prog.asiaventure.elements.structure;

import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;


public abstract class ElementStructurel extends Entite {
	
	/**
	 * Crée un élement structurel
	 * @param  nom Le nom de l'élément
	 * @param  m   Le monde auquel il est rattaché
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique que l'élement structurel est déjà présent
	 */
	public ElementStructurel (String nom, Monde m) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, m);
	}
}