// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
package fr.insarouen.asi.prog.asiaventure;

import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.io.Serializable;

public class Monde implements Serializable {
	private String nom;
	private HashMap <String, Entite> hmEntite = new HashMap <String, Entite> ();

	/**
	 * Crée un monde
	 * @param nom Le nom du monde
	 */
	public Monde(String nom) {
		this.nom = nom;
	}

	/**
	 * Ajoute une entité à un monde
	 * @param ent l'entité
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'on essaie d'ajouter une entité avec un nom qui existe déjà
	 */
	public void ajouter(Entite ent) throws NomDEntiteDejaUtiliseDansLeMondeException {
		if (this.getEntite(ent.getNom()) != null)
			throw new NomDEntiteDejaUtiliseDansLeMondeException("L'entité " + ent.getNom() + " que vous essayé d'ajouter est déjà présente dans le monde " + this.nom);

		// On ajoute dans la hash map
		this.hmEntite.put(ent.getNom(), ent);
	}

	/**
	 * Retourne une entité d'un monde selon son nom
	 * @param  nom Le nom de l'entité
	 * @return L'entité recherchée, ou null
	 */
	public Entite getEntite(String nom) {
		return this.hmEntite.get(nom);
	}

	public HashMap <String, Entite> getEntites() {
		return this.hmEntite;
	}

	public String toString() {
		StringBuilder chaineBuilder = new StringBuilder("Monde : "+this.nom+" \n");

		// Récupération des entités
		Collection <Entite> collectionEntites = this.hmEntite.values();
		
		// Itération sur chaque entité
		for (Iterator <Entite> i = collectionEntites.iterator(); i.hasNext();) {
			Entite ent = i.next();
			chaineBuilder.append("Entite : ");
			chaineBuilder.append(ent.getNom() + "\n");
		}

		return chaineBuilder.toString();
	}
}