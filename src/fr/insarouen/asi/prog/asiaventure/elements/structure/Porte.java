// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
package fr.insarouen.asi.prog.asiaventure.elements.structure;

import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Clef;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;


public class Porte extends ElementStructurel implements Activable {
	private Piece pieceA;
	private Piece pieceB;
	private Etat etat;
	private Serrure serrure = null;
	
	/**
	 * @brief Crée une porte avec deux pièces
	 * @param nom Le nom de la porte
	 * @param m Le monde associé
	 * @param pieceA La pièce A
	 * @param pieceB La pièce B
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'on essaie d'ajouter une entité avec un nom qui existe déjà
	 */
	public Porte(String nom, Monde m, Piece pieceA, Piece pieceB) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, m);
		this.etat = Etat.FERME;
		this.pieceA = pieceA;
		this.pieceB = pieceB;
		pieceA.addPorte(this);
		pieceB.addPorte(this);
	}

	/**
	 * @brief Crée une porte avec deux pièces, verrouillée par une serrure
	 * @param nom Le nom de la porte
	 * @param s La serrure associée à la porte
	 * @param m Le monde associé
	 * @param pieceA La pièce A
	 * @param pieceB La pièce B
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'on essaie d'ajouter une entité avec un nom qui existe déjà
	 */
	public Porte(String nom, Serrure s, Monde m, Piece pieceA, Piece pieceB) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, m);
		this.etat = Etat.VERROUILLE;
		this.serrure = s;
		this.pieceA = pieceA;
		this.pieceB = pieceB;
		pieceA.addPorte(this);
		pieceB.addPorte(this);
	}

	/**
	 * @brief Détermine si une porte est activable avec un objet
	 * @param obj L'objet en question
	 * @return true si la porte est activable avec l'objet, false sinon
	 */
	public boolean activableAvec(Objet obj) {
		if (this.serrure == null)
			return (obj instanceof PiedDeBiche);
		else
			return this.serrure.activableAvec(obj);
	}

	/**
	 * @brief Active une porte
	 * @throws ActivationImpossibleException Indique qu'il est impossible d'activer la porte
	 */
	public void activer() throws ActivationImpossibleException {
		if (this.getEtat() == Etat.FERME)
			this.etat = Etat.OUVERT;
		else {
			if (this.getEtat() == Etat.OUVERT)
				this.etat = Etat.FERME;
		}

		if (this.getEtat() == Etat.CASSE || this.getEtat() == Etat.VERROUILLE)
			throw new ActivationImpossibleException("Impossible d'activer la porte " + this.getNom());
	}

	/**
	 * @brief Active une porte avec un objet
	 * @param obj L'objet à utiliser pour activer la porte
	 * @throws ActivationImpossibleException Indique qu'il est impossible d'activer la porte
	 * @throws ActivationImpossibleAvecObjetException Indique qu'il est impossible d'activer la porte avec cet objet
	 */
	public void activerAvec(Objet obj) throws ActivationImpossibleAvecObjetException, ActivationImpossibleException {
		if (this.activableAvec(obj) && this.getEtat() != Etat.CASSE) {
			// Si on a une clé, on active aussi la serrure associée
			// Si on a un pied de biche, on casse la porte et la serrure

			if ((obj instanceof Clef && this.serrure != null) || (obj instanceof PiedDeBiche)) {
				
				// Il existe une serrure donc on l'active
				if (this.serrure != null)
					this.serrure.activerAvec(obj);
				
				// On a une clé, on joue sur la serrure
				if (obj instanceof Clef && this.serrure != null) {	
					if (this.getEtat() == Etat.VERROUILLE)
						this.etat = Etat.OUVERT;
					else if (this.getEtat() == Etat.FERME || this.getEtat() == Etat.OUVERT) 
						this.etat = Etat.VERROUILLE;
				}
				// On a un pied de biche
				else
					this.etat = Etat.CASSE;
			}
			// Pas de serrure, on tente juste d'activer la porte
			else
				this.activer();
		}
		else
			throw new ActivationImpossibleAvecObjetException("Impossible d'activer la porte " + this.getNom() + " avec l'objet " + obj.getNom());
	}

	/**
	 * @brief Retourne l'état d'une porte
	 * @return L'état de la porte
	 */
	public Etat getEtat() {
		return this.etat;
	}

	/**
	 * @brief Indique la pièce de l'autre côté de la porte
	 * @param piece La pièce où l'on se trouve
	 * @return La pièce de l'autre côté de la porte
	 */
	public Piece getPieceAutreCote(Piece piece) {
		if (piece.equals(this.pieceA))
			return this.pieceB;

		if (piece.equals(this.pieceB))
			return this.pieceA;

		return null;
	}

	public Serrure getSerrure() {
		return this.serrure;
	}

	public String toString() {
		return "Porte " + this.getNom() + " ayant l'état " + this.getEtat() + "\n";
	}
}