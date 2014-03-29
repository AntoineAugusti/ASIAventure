// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/vivants/*.java
// Éxecution : java -classpath ./classes/ fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
package fr.insarouen.asi.prog.asiaventure.elements.structure;

import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.VivantAbsentDeLaPieceException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

public class Piece extends ElementStructurel {
	private HashMap <String, Objet> hmObjets = new HashMap <String, Objet> ();
	private HashMap <String, Vivant> hmVivants = new HashMap <String, Vivant> ();
	private HashMap <String, Porte> hmPortes = new HashMap <String, Porte> ();

	/**
	 * Crée une pièce
	 * @param  nom Le nom de la pièce
	 * @param  m   Le monde auquel la pièce est rattachée
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'une pièce existe déjà avec ce nom
	 */
	public Piece(String nom, Monde m) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, m);
	}

	/**
	 * Dépose un objet dans une pièce
	 * @param obj L'objet à déposer
	 */
	public void deposer(Objet obj) {
		this.hmObjets.put(obj.getNom(), obj);
	}

	/**
	 * @brief Ajoute une porte à une pièce
	 * 
	 * @param porte La porte en question
	 */
	protected void addPorte(Porte porte) {
		this.hmPortes.put(porte.getNom(), porte);
	}

	/**
	 * @brief Détermine si une pièce a une porte
	 * 
	 * @param porte La porte en question
	 * @return true si la pièce possède la porte, false sinon
	 */
	public boolean aLaPorte(Porte porte) {
		return this.hmPortes.containsKey(porte.getNom());
	}

	/**
	 * @brief Détermine si une pièce a une porte
	 * 
	 * @param porte La porte en question
	 * @return true si la pièce possède la porte, false sinon
	 */
	public boolean aLaPorte(String nomPorte) {
		return this.hmPortes.containsKey(nomPorte);
	}

	/**
	 * @brief Retourne la porte selon son nom
	 * 
	 * @param porte La porte en question
	 * @return La porte en question ou null
	 */
	public Porte getPorte(String nomPorte) {
		return this.hmPortes.get(nomPorte);
	}

	/**
	 * Fait rentrer un vivant dans une pièce
	 * @param viv Le vivant
	 */
	public void entrer(Vivant viv) {
		this.hmVivants.put(viv.getNom(), viv);
	}

	/**
	 * Détermine si un objet est dans une pièce selon son nom
	 * @param  nomObjet Le nom de l'objet
	 * @return true si l'objet est dans la pièce, false sinon
	 */
	public boolean contientObjet(String nomObjet) {
		return this.hmObjets.containsKey(nomObjet);
	}

	/**
	 * Détermine si un objet est dans une pièce
	 * @param  obj L'objet en question
	 * @return true si l'objet est dans la pièce, false sinon
	 */
	public boolean contientObjet(Objet obj) {
		return this.contientObjet(obj.getNom());
	}

	/**
	 * Détermine si un vivant est dans une pièce
	 * @param  nomViv Le vivant
	 * @return        true si le vivant est dans la pièce, false sinon
	 */
	public boolean contientVivant(String nomViv) {
		return this.hmVivants.containsKey(nomViv);
	}

	/**
	 * Détermine si un vivant est dans une pièce
	 * @param  nomViv Le vivant
	 * @return        true si le vivant est dans la pièce, false sinon
	 */
	public boolean contientVivant(Vivant viv) {
		return this.contientVivant(viv.getNom());
	}

	/**
	 * Retire un objet dans une pièce. Ne retire rien si l'objet n'est pas dans la pièce ou si l'objet n'est pas déplaçable
	 * @param  nomObjet L'objet
	 * @return L'objet retiré si celui-ci a été retiré, null si rien n'a été retiré
	 * @throws ObjetAbsentDeLaPieceException Indique q'un objet est absent de la pièce
	 * @throws ObjetNonDeplacableDeLaPieceException Indique q'un objet est non déplaçable
	 */
	public Objet retirer(String nomObjet) throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		// Le monde possède une méthode getEntite qui retourne une Entite
		// Objet hérite d'Entite
		Entite e = this.getMonde().getEntite(nomObjet);
		if (e instanceof Objet)
			return this.retirer((Objet) e);
		else
			return null;
	}

	/**
	 * Retire un objet dans une pièce. Ne retire rien si l'objet n'est pas dans la pièce ou si l'objet n'est pas déplaçable
	 * @param  nomObjet L'objet
	 * @return L'objet retiré si celui-ci a été retiré, null si rien n'a été retiré
	 * @throws ObjetAbsentDeLaPieceException Indique q'un objet est absent de la pièce
	 * @throws ObjetNonDeplacableDeLaPieceException Indique q'un objet est non déplaçable
	 */
	public Objet retirer(Objet obj) throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		// Est-ce que l'objet est dans la pièce ?
		if (!this.contientObjet(obj))
			throw new ObjetAbsentDeLaPieceException("L'objet " + obj.getNom() +" est absent de la pièce " + this.getNom());
		// L'objet est bien dans la pièce, on le retire
		else {

			// Est-il déplaçable ?
			if (obj.estDeplacable()) {
				this.hmObjets.remove(obj.getNom());
				
				return obj;
			}
			else
				throw new ObjetNonDeplacableDeLaPieceException("L'objet " + obj.getNom() +" de la pièce " + this.getNom()+" est non déplaçable.");
		}
	}

	/**
	 * Sort un vivant d'une pièce, ne fait rien si celui-ci n'est pas dans la pièce ou si celui-ci est mort
	 * @param  viv Le vivant en question
	 * @return     Le vivant retiré si celui-ci a bien été retiré, null sinon
	 * @throws VivantAbsentDeLaPieceException Indique qu'un vivant est absent de la pièce
	 */
	public Vivant sortir(Vivant viv) throws VivantAbsentDeLaPieceException {
		// Est-ce que le vivant est dans la pièce ?
		if (!this.contientVivant(viv) || viv.estMort())
			throw new VivantAbsentDeLaPieceException("Le vivant " + viv.getNom() + "est absent de la pièce "+ this.getNom() + " et ne peut donc pas être sorti");
		// Le vivant est bien dans la pièce, on le retire
		else {
			this.hmVivants.remove(viv.getNom());

			// On retourne le vivant retiré de la pièce
			return viv;
		}
	}

	public HashMap <String, Porte> getPortes() {
		return this.hmPortes;
	}

	public HashMap <String, Objet> getObjets() {
		return this.hmObjets;
	}

	/**
	 * Sort un vivant d'une pièce, ne fait rien si celui-ci n'est pas dans la pièce ou si celui-ci est mort
	 * @param  viv Le vivant en question
	 * @return     Le vivant retiré si celui-ci a bien été retiré, null sinon
	 * @throws VivantAbsentDeLaPieceException Indique qu'un vivant est absent de la pièce
	 */
	public Vivant sortir(String nomViv) throws VivantAbsentDeLaPieceException {
		// Le monde possède une méthode getEntite qui retourne une Entite
		// Objet hérite d'Entite
		Entite e = this.getMonde().getEntite(nomViv);
		if (e instanceof Vivant)
			return this.sortir((Vivant) e);
		else
			return null;
	}

	public String toString() {
		StringBuilder chaineBuilder = new StringBuilder(super.toString());

		chaineBuilder.append("Objets : \n");
		// Récupération des objets
		Collection <Objet> collectionObjets = this.hmObjets.values();
		
		// Itération sur chaque objet
		for (Iterator <Objet> i = collectionObjets.iterator(); i.hasNext();) {
			Objet obj = i.next();
			chaineBuilder.append(obj.toString());
		}

		chaineBuilder.append("Vivants : \n");
		// Récupération des vivants
		Collection <Vivant> collectionVivants = this.hmVivants.values();
		
		// Itération sur chaque vivant
		for (Iterator <Vivant> i = collectionVivants.iterator(); i.hasNext();) {
			Vivant obj = i.next();
			chaineBuilder.append(obj.toString());
		}

		return chaineBuilder.toString();
	}
}