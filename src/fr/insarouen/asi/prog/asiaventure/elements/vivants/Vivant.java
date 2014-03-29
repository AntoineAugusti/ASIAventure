// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/vivants/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java
// Éxécution : java -classpath ./classes/ fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;
package fr.insarouen.asi.prog.asiaventure.elements.vivants;

import fr.insarouen.asi.prog.asiaventure.ASIAventureException;
import fr.insarouen.asi.prog.asiaventure.elements.Executable;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.Monde;
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

public abstract class Vivant extends Entite implements Executable {
	private HashMap <String, Objet> hmObjets = new HashMap <String, Objet> ();
	private int pointVie;
	private int pointForce;
	private Piece piece;

	/**
	 * Crée un vivant
	 * @param  nom        Son nom
	 * @param  monde      Le monde auquel il est rattaché
	 * @param  pointVie   Son nombre de point de vie
	 * @param  pointForce Son nom de points de force
	 * @param  piece      La pièce dans laquelle se trouve le vivant
	 * @param  objets     Les objets que possède le vivant
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'un vivant du même nom existe déjà
	 */
	public Vivant(String nom, Monde monde, int pointVie, int pointForce, Piece piece, Objet... objets) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde);
		this.pointVie = pointVie;
		this.pointForce = pointForce;
		this.piece = piece;
		// On ne peut pas faire un putAll si facilement.
		// On parcourt le tableau et on fait un put sur chaque élément
		for (int i = 0; i < objets.length ; i++) {
			this.hmObjets.put(objets[i].getNom(), objets[i]);
		}

		piece.entrer(this);
	}

	public void setPointsDeVie(int pv) {
		this.pointVie = pv;
	}

	/**
	 * Détermine si un vivant est mort
	 * @return true si le vivant est mort, false sinon
	 */
	public boolean estMort() {
		return (this.getPointVie() == 0);
	}

	/**
	 * Getter pour le nombre de points de vie
	 * @return Le nombre de points de vie
	 */
	public int getPointVie() {
		return this.pointVie;
	}

	/**
	 * Getter pour le nombre de points de force
	 * @return Le nombre de points de force
	 */
	public int getPointForce() {
		return this.pointForce;
	}

	/**
	 * Getter pour la pièce
	 * @return La pièce dans laquelle se trouve le vivant
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * Fait prendre un objet à un vivant. Le vivant essaie de le retirer dans la pièce dans laquelle il se trouve. Si celui-ci n'arrive pas à le retirer de la pièce il ne le prend pas
	 * @param obj L'objet
	 * @throws ObjetAbsentDeLaPieceException Indique que l'objet est absent de la pièce
	 * @throws ObjetNonDeplacableDeLaPieceException Indique que l'objet est non-déplaçable
	 */
	public void prendre(Objet obj) throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		Objet o = this.getPiece().retirer(obj);

		// Si on a vraiment retiré l'objet de la pièce
		if (o != null) {
			this.getObjets().put(o.getNom(), o);
		}
	}

	/**
	 * @brief Fait franchir une porte à un vivant
	 * 
	 * @param nomPorte La porte en question
	 * @throws PorteFermeException La porte est fermée et ne peut pas être franchie
	 * @throws PorteInexistanteDansLaPieceException La porte est inexistante
	 */
	public void franchir(String nomPorte) throws PorteFermeException, PorteInexistanteDansLaPieceException {
		Porte porte = this.getPiece().getPorte(nomPorte);
		
		if (porte == null)
			throw new PorteInexistanteDansLaPieceException("La porte " + nomPorte + " est inexistante dans la pièce " + this.getPiece().getNom());
		if (porte.getEtat() == Etat.FERME)
			throw new PorteFermeException("La porte " + nomPorte + " est fermée.");

		try {
			// On quitte la pièce actuelle
			this.piece.sortir(this);
			// On change la pièce du vivant
			this.piece = porte.getPieceAutreCote(this.piece);
			// On "rentre" dans la nouvelle pièce
			this.piece.entrer(this);
		}
		catch (VivantAbsentDeLaPieceException e){
			throw new RuntimeException("Attention ne doit jamais se produire !");
		}
	}
	
	/**
	 * @brief Fait franchir une porte à un vivant
	 * 
	 * @param porte La porte en question
	 * @throws PorteFermeException La porte est fermée et ne peut pas être franchie
	 * @throws PorteInexistanteDansLaPieceException La porte est inexistante
	 */
	public void franchir(Porte porte) throws PorteFermeException, PorteInexistanteDansLaPieceException {
		this.franchir(porte.getNom());
	}

	/**
	 * @brief Active un activable
	 * 
	 * @param activable L'activable en question
	 */
	public void activerActivable(Activable activable) throws ActivationException {
		activable.activer();
	}

	/**
	 * @brief Active un activable à l'aide d'un objet
	 * 
	 * @param activable L'activable en question
	 * @param objet L'objet en question
	 */
	public void activerActivableAvecObjet(Activable activable, Objet objet) throws ActivationException {
		activable.activerAvec(objet);
	}

	/**
	 * Fait prendre un objet à un vivant. Le vivant essaie de le retirer dans la pièce dans laquelle il se trouve. Si celui-ci n'arrive pas à le retirer de la pièce il ne le prend pas
	 * @param nomObj L'objet
	 * @throws ObjetAbsentDeLaPieceException Indique que l'objet est absent de la pièce
	 * @throws ObjetNonDeplacableDeLaPieceException Indique que l'objet est non-déplaçable
	 */
	public void prendre(String nomObj) throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		Entite e = this.getMonde().getEntite(nomObj);
		if (e instanceof Objet)
			this.prendre((Objet) e);
	}

	/**
	 * Détermine si un vivant possède un objet
	 * @param  obj L'objet en question
	 * @return     true si le vivant le possède, false sinon
	 */
	public boolean possede(Objet obj) {
		return this.getObjets().containsKey(obj.getNom());
	}

	public HashMap <String,Objet> getObjets() {
		return this.hmObjets;
	}

	/**
	 * Retourne un objet présent dans l'inventaire d'un vivant
	 * @param  nomObjet Le nom de l'objet
	 * @return          l'objet si celui-ci est dans son inventaire, null sinon
	 */
	public Objet getObjet(String nomObjet) {
		return this.getObjets().get(nomObjet);
	}

	/**
	 * Dépose un objet de l'inventaire du vivant dans la pièce courante. Si le vivant ne le possède pas, rien ne se passe pas.
	 * @param obj L'objet à déposer.
	 * @throws ObjetNonPossedeParLeVivantException Indique que l'objet n'est pas possédé par le vivant
	 */
	public void deposer(Objet obj) throws ObjetNonPossedeParLeVivantException {
		// L'objet n'était pas présent, on n'a pas pu le déposer
		if (this.getObjets().remove(obj.getNom()) == null)
			throw new ObjetNonPossedeParLeVivantException("L'objet " + obj.getNom() + " n'est pas possédé par le vivant " + this.getNom() + " et ne peut donc pas être déposé.");
		// On a bien pu déposer l'objet
		else
			this.getPiece().deposer(obj);
	}

	/**
	 * Dépose un objet de l'inventaire du vivant dans la pièce courante. Si le vivant ne le possède pas, rien ne se passe pas.
	 * @param obj L'objet à déposer.
	 * @throws ObjetNonPossedeParLeVivantException Indique que l'objet n'est pas possédé par le vivant
	 */
	public void deposer(String nomObjet) throws ObjetNonPossedeParLeVivantException {
		this.deposer(this.getObjet(nomObjet));
	}

	public String toString() {
		return "Vivant : "+this.getNom()+" pointVie : "+this.getPointVie()+"; pointForce : "+this.getPointForce()+"; Piece : "+this.getPiece().getNom().toString()+"\n";
	}
}