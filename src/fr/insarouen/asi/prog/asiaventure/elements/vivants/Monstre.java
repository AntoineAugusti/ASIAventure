// Compilation : javac -sourcepath ./src -d ./classes ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/serrurerie/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java  ./src/fr/insarouen/asi/prog/asiaventure/elements/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/vivants/*.java ./src/fr/insarouen/asi/prog/asiaventure/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/objets/*.java ./src/fr/insarouen/asi/prog/asiaventure/elements/structure/*.java
package fr.insarouen.asi.prog.asiaventure.elements.vivants;

import fr.insarouen.asi.prog.asiaventure.elements.Executable;
import fr.insarouen.asi.prog.asiaventure.ASIAventureException;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Porte;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Activable;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Clef;
import fr.insarouen.asi.prog.asiaventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.PorteFermeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetNonDeplacableDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.ObjetNonPossedeParLeVivantException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator; 


public class Monstre extends Vivant {
	
	/**
	 * Crée un monstre
	 * @param  nom        Son nom
	 * @param  monde      Le monde auquel il est rattaché
	 * @param  pointVie   Son nombre de point de vie
	 * @param  pointForce Son nom de points de force
	 * @param  piece      La pièce dans laquelle se trouve le vivant
	 * @param  objets     Les objets que possède le vivant
	 * @throws NomDEntiteDejaUtiliseDansLeMondeException Indique qu'un vivant du même nom existe déjà
	 */
	public Monstre(String nom, Monde monde, int pointVie, int pointForce, Piece piece, Objet... objets) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde, pointVie, pointForce, piece, objets);
	}

	public void executer() {
		if (!this.estMort()) {
			// Récupération des portes
			Collection <Porte> collectionPorte = this.getPiece().getPortes().values();
			
			// Itération sur chaque porte
			Porte porteFranchissable = null;
			for (Iterator <Porte> i = collectionPorte.iterator(); i.hasNext();) {
				Porte porte = i.next();
				if (porteFranchissable == null && (porte.getEtat() == Etat.OUVERT || porte.getEtat() == Etat.FERME || porte.getEtat() == Etat.CASSE))
					porteFranchissable = porte;
			}

			// Si la porte est fermée il faut d'abord l'activer
			if (porteFranchissable.getEtat() == Etat.FERME) {
				try {
					porteFranchissable.activer();
				}
				catch (ASIAventureException e) {
					System.out.println(e.getMessage());
				}
			}

			// On franchit la porte et on décrémente les PDV
			try {
				this.franchir(porteFranchissable);
			}
			catch (ASIAventureException e) {
				System.out.println(e.getMessage());
			}

			this.setPointsDeVie(this.getPointVie() - 1);

			// On prend les objets dans la pièce et on dépose les objets possédés dans la pièce
			if (!this.estMort()) {
				Collection <Objet> objetsPiece = this.getPiece().getObjets().values();
				Collection <Objet> objetsInventaire = this.getObjets().values();

				for (Iterator <Objet> i = objetsPiece.iterator(); i.hasNext();) {
					try {
						this.prendre(i.next());
					}
					catch (ASIAventureException e) {
						System.out.println(e.getMessage());
					}
				}

				for (Iterator <Objet> i = objetsInventaire.iterator(); i.hasNext();) {
					try {
						this.deposer(i.next());
					}
					catch (ASIAventureException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
}