// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java
// Exécution : java -classpath ./classes/ fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBichepackage fr.insarouen.asi.prog.asiaventure.elements.objets;
package fr.insarouen.asi.prog.asiaventure.elements.objets;

import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;

// Objet est dans le même package donc pas besoin de l'import
public class PiedDeBiche extends Objet {

	/**
	 * Crée un pied de biche
	 * @param  nom   Son nom
	 * @param  monde Le monde auquel il est rattaché
	 */
	public PiedDeBiche (String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde);
	}

	/**
	 * Indique si un pied de biche est déplaçable
	 * @return true si déplaçable, false sinon
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'un pied de biche existe déjà avec ce nom
	 */
	public boolean estDeplacable() {
		return true;
	}
}