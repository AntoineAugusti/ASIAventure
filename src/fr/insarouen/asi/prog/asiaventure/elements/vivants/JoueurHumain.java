// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/vivants/*.java
package fr.insarouen.asi.prog.asiaventure.elements.vivants;

import fr.insarouen.asi.prog.asiaventure.ASIAventureException;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Executable;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Porte;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Activable;
import fr.insarouen.asi.prog.asiaventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.PorteFermeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetNonDeplacableDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.ObjetNonPossedeParLeVivantException;
import java.util.HashMap;

public class JoueurHumain extends Vivant {
	private String ordre;

	/**
	 * Crée un JoueurHumain
	 * @param  nom        Son nom
	 * @param  monde      Le monde auquel il est rattaché
	 * @param  pointVie   Son nombre de point de vie
	 * @param  pointForce Son nom de points de force
	 * @param  piece      La pièce dans laquelle se trouve le vivant
	 * @param  objets     Les objets que possède le vivant
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'un vivant du même nom existe déjà
	 */
	public JoueurHumain(String nom, Monde monde, int pointVie, int pointForce, Piece piece, Objet... objets) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde, pointVie, pointForce, piece, objets);
	}

	public void setOrdre(String ordre) {
		this.ordre = ordre;
	}

	/**
	 * @brief Prendre un objet
	 * @param nomObjet Le nom de l'objet en question
	 */
	void commandePrendre(String nomObjet) throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		prendre(nomObjet);
	}

	/**
	 * @brief Poser un objet
	 * @param nomObjet Le nom de l'objet en question
	 */
	void commandePoser(String nomObjet) throws ObjetNonPossedeParLeVivantException {
		deposer(nomObjet);
	}

	/**
	 * @brief Franchit une porte
	 * @param nomPorte Le nom de la porte en question
	 */
	void commandeFranchir(String nomPorte) throws PorteFermeException, PorteInexistanteDansLaPieceException {
		franchir(nomPorte);
	}

	/**
	 * @brief Ouvre une porte
	 * @param nomPorte Le nom de la porte en question
	 */
	void commandeOuvrirPorte(String nomPorte) throws ActivationException {
		Porte p = this.getPiece().getPorte(nomPorte);

		activerActivable(p);
	}

	/**
	 * @brief Ouvre une porte
	 * @param nomPorte Le nom de la porte en question
	 * @param nomObjet Le nom de l'objet en question
	 */
	void commandeOuvrirPorte(String nomPorte, String nomObjet) throws ActivationException {
		Porte p = this.getPiece().getPorte(nomPorte);
		Objet o = this.getObjet(nomObjet);

		activerActivableAvecObjet(p, o);
	}

	public void executer() {

	}
}