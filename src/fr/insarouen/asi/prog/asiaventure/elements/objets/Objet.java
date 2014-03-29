// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java
package fr.insarouen.asi.prog.asiaventure.elements.objets;

import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;


public abstract class Objet extends Entite {

	/**
	 * Crée un objet
	 * @param  nom Le nom de l'objet
	 * @param  monde Le monde rattaché à l'objet
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException indique qu'un objet existe déjà avec ce nom
	 */
	public Objet (String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde);
	}

	public abstract boolean estDeplacable();

	public String toString() {
		return super.toString() + "estDeplacable : " + this.estDeplacable();
	}
}