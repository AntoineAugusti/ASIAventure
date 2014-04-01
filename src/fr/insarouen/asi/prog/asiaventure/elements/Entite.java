// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java
package fr.insarouen.asi.prog.asiaventure.elements;

import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import java.io.Serializable;

public abstract class Entite implements Serializable {
	public static final int MAXIMUMNOMALEATOIRE = 200;
	private Monde monde;
	private String nom;

	/**
	 * Crée une entité. L'entité est rattachée au monde donné en paramètres.
	 * @param  nom Le nom de l'entité
	 * @param  m   Le monde auquel on doit rattacher l'entité
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'on essaie d'ajouter une entité avec un nom qui existe déjà
	 */
	public Entite(String nom, Monde m) throws NomDEntiteDejaUtiliseDansLeMondeException {
		this.nom = nom;
		this.monde = m;
		m.ajouter(this);
	}

	/**
	 * Getter pour le nom d'une entité
	 * @return Le nom de l'entité
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Getter pour le monde d'une entité
	 * @return Le monde de l'entité
	 */
	public Monde getMonde() {
		return this.monde;
	}

	/**
	 * Redéfinition de l'equals pour comparer une entité. Vérifie que le monde ET le nom sont identiques
	 * @param  o L'objet à tester
	 * @return   true si equals, false sinon
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Entite))
			return false;
		Entite ent = (Entite) o;
		// On teste le monde par référence
		// Nom est une CdC donc on DOIT tester avec equals
		return this.getMonde() == ent.getMonde() && this.getNom().equals(ent.getNom());
	}

	public String toString() {
		return "Entite : " + this.getClass().getSimpleName() + "; Nom : " + this.getNom() + ";\n";
	}
}