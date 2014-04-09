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
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Arrays;

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
	public void commandePrendre(String nomObjet) throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableDeLaPieceException {
		prendre(nomObjet);
	}

	/**
	 * @brief Poser un objet
	 * @param nomObjet Le nom de l'objet en question
	 */
	public void commandePoser(String nomObjet) throws ObjetNonPossedeParLeVivantException {
		deposer(nomObjet);
	}

	/**
	 * @brief Franchit une porte
	 * @param nomPorte Le nom de la porte en question
	 */
	public void commandeFranchir(String nomPorte) throws PorteFermeException, PorteInexistanteDansLaPieceException {
		franchir(nomPorte);
	}

	/**
	 * @brief Ouvre une porte
	 * @param nomPorte Le nom de la porte en question
	 */
	public void commandeOuvrirPorte(String nomPorte) throws ActivationException {
		Porte p = this.getPiece().getPorte(nomPorte);

		activerActivable(p);
	}

	/**
	 * @brief Ouvre une porte
	 * @param nomPorte Le nom de la porte en question
	 * @param nomObjet Le nom de l'objet en question
	 */
	public void commandeOuvrirPorte(String nomPorte, String nomObjet) throws ActivationException {
		Porte p = this.getPiece().getPorte(nomPorte);
		Objet o = this.getObjet(nomObjet);

		activerActivableAvecObjet(p, o);
	}

	public void executer() throws Throwable {
		Method m = this.getMethodeOrdre(this.ordre.split("\\s+"));
		try {
			m.invoke(this, (Object[]) this.getParametresOrdre(this.ordre.split("\\s+")));
		}
		catch (InvocationTargetException e) {
			throw e.getCause();
		}
		catch (Exception e) {
			throw new CommandeImpossiblePourLeVivantException("Impossible de lancer cette commande");
		}
	}

	/**
	 * @brief Cherche les paramètres à donner à la méthode pour exécuter un ordre
	 * 
	 * @param parametres Le tableau des String qui correspondent à l'ordre
	 * @return [description]
	 */
	private String[] getParametresOrdre(String[] parametres) {
		String[] resultat = null;
		
		if (parametres.length > 2) {
			resultat = new String[2];
			resultat[0] = parametres[1];
			resultat[1] = parametres[3];
		}
		else {
			resultat = new String[1];
			resultat[0] = parametres[1];
		}

		return resultat;
	}

	/**
	 * @brief Supprime un élément d'un tableau
	 * 
	 * @param entree Le tableau de chaînes
	 * @param aSupprimer La chaîne à supprimer du tableau
	 * 
	 * @return Le tableau
	 */
	private static String[] supprimerElement(String[] entree, String aSupprimer) {
		List <String> resultat = new LinkedList <String>();

		for (String item : entree)
			if (!aSupprimer.equals(item))
				resultat.add(item);

		return resultat.toArray(entree);
	}

	/**
	 * @brief Cherche la méthode à appeler en fonction de la commande tapée
	 * 
	 * @param parametres Le tableau des String qui correspondent à l'ordre
	 * @return La méthode à appeler
	 */
	private Method getMethodeOrdre(String[] parametres) throws Throwable {
		// Pour mimer un ucfirst
		String baseNomMethode = parametres[0].substring(0,1).toUpperCase() + parametres[0].substring(1);

		// Nom des classes pour déterminer la signature de la méthode
		String[] params = this.getParametresOrdre(parametres);
		Class[] parametresFormels = new Class[params.length];

		for (int i=0; i<params.length; i++) {
			parametresFormels[i] = params[i].getClass();
		}

		return this.getClass().getMethod("commande"+baseNomMethode, parametresFormels);
	}

	public String toString() {
		StringBuilder chaineBuilder = new StringBuilder("*** JoueurHumain : " + this.getNom() + "; PDV : "+ this.getPointVie() + "; Pièce : " + this.getPiece().getNom().toString() + ".\n");
		
		// Objets dans la pièce
		if (this.getPiece().getObjets().values().size() >= 1) {
			chaineBuilder.append("** Objets dans la pièce\n");
			// Itération sur chaque entité
			for (Iterator <Objet> i = this.getPiece().getObjets().values().iterator(); i.hasNext();) {
				Objet o = i.next();
				chaineBuilder.append("\t * " + o.getNom());
			}
			chaineBuilder.append("\n");
		}

		// Inventaire
		if (this.getObjets().values().size() >= 1) {
			chaineBuilder.append("** Objets dans l'inventaire\n");
			// Itération sur chaque entité
			for (Iterator <Objet> i = this.getObjets().values().iterator(); i.hasNext();) {
				Objet o = i.next();
				chaineBuilder.append("\t * " + o.getNom() + "\n");
			}
			chaineBuilder.append("\n");
		}

		// Portes dans la pièce
		if (this.getPiece().getPortes().values().size() >= 1) {
			chaineBuilder.append("** Portes\n");
			// Itération sur chaque entité
			for (Iterator <Porte> i = this.getPiece().getPortes().values().iterator(); i.hasNext();) {
				Porte p = i.next();
				chaineBuilder.append("\t * " + p.toString());
			}
			chaineBuilder.append("\n");
		}

		return chaineBuilder.toString();
	}
}