// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/serrurerie/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
package fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie;

import fr.insarouen.asi.prog.asiaventure.elements.structure.Activable;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationImpossibleException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationImpossibleAvecObjetException;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;

import java.util.Random;


public class Serrure extends Objet implements Activable {
	
	private boolean clefDejaCree = false;
	private Etat etat;

	/**
	 * Crée une serrure
	 * @param  nom Le nom de la serrure
	 * @param  monde Le monde rattaché à la serrure
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException indique qu'un objet existe déjà avec ce nom
	 */
	public Serrure(String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde);
		this.etat = Etat.VERROUILLE;
	}

	/**
	 * Crée une serrure avec un nom aléatoire
	 * @param monde Le monde rattaché 
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException indique qu'un objet existe déjà avec ce nom
	 */
	public Serrure(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(Integer.toString(Serrure.getNombreAleatoire(monde)), monde);
	}

	private static int getNombreAleatoire(Monde m) {
		int nombre = 0;
		do {
			Random r = new Random();
			nombre = r.nextInt(MAXIMUMNOMALEATOIRE);	
		} while (m.getEntite(Integer.toString(nombre)) != null);

		return nombre;
	}

	/**
	 * Active une serrure avec un objet
	 * @param obj L'objet utilisé pour activer la serrure
	 */
	public void activerAvec(Objet obj) throws ActivationImpossibleAvecObjetException {
		if (!this.activableAvec(obj))
			throw new ActivationImpossibleAvecObjetException("Impossible d'activer la serrure " + this.getNom() + " avec l'objet " + obj.getNom());
		else {
			if (this.getEtat() == Etat.VERROUILLE)
				this.etat = Etat.DEVERROUILLE;
			else {
				if (this.getEtat() == Etat.DEVERROUILLE)
					this.etat = Etat.VERROUILLE;
			}
		}

		// Si on active la serrure avec un pied de biche, la serrure est cassée ensuite
		if (obj instanceof PiedDeBiche)
			this.etat = Etat.CASSE;
	}

	public void activer() throws ActivationImpossibleException {
		throw new ActivationImpossibleException("Impossible d'activer la serrure " + this.getNom());
	}

	/**
	 * @brief Crée une clef associée à une serrure. Retourne une clé une seule fois, les autres fois un null
	 * @return Un objet Clef si la clef a été crée pour la première fois, null sinon
	 */
	public Clef creerClef() throws NomDEntiteDejaUtiliseDansLeMondeException {
		if (!this.clefDejaCree) {
			clefDejaCree = true;
			return new Clef(this.getNom() + "_cle", this.getMonde());
		}
		else
			return null;
	}

	/**
	 * @brief Retourne l'état d'une serrure
	 * @return L'état de la serrure
	 */
	public Etat getEtat() {
		return this.etat;
	}

	/**
	 * Détermine si la serrure est activable avec un objet
	 * 
	 * @param obj L'objet à tester
	 * @return true si la serrure est activable avec l'objet, false sinon
	 */
	public boolean activableAvec(Objet obj) {
		return ((obj instanceof Clef && obj.getNom().equals(this.getNom() + "_cle")) || (obj instanceof PiedDeBiche));
	}

	/**
	 * @brief Détermine si une serrure est déplaçable ou non
	 * @return true si c'est déplaçable, false sinon
	 */
	public boolean estDeplacable() {
		return false;
	}

	public String toString() {
		return "Serrure -- nom : " + this.getNom() + " monde : " + this.getMonde() + " clefDejaCree" + this.clefDejaCree + " etat : " + this.getEtat();
	}
}