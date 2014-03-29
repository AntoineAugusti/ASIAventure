package fr.insarouen.asi.prog.asiaventure.elements.objets;

import fr.insarouen.asi.prog.asiaventure.elements.structure.Activable;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationException;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;

public class Coffre extends Objet implements Activable {
	private Etat etat;
	
	public Coffre(String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom, monde);
		this.etat = Etat.FERME;
	}

    public boolean estDeplacable() {
    	return false;
    }

    public void activer() throws ActivationException {

    }

    public void activerAvec(Objet obj) throws ActivationException {
    	
    }

    public boolean activableAvec(Objet obj) {
        return (obj instanceof PiedDeBiche);
    }

    public Etat getEtat() {
    	return this.etat;
    }

    public String toString() {
    	return "Coffre -- nom : " + this.getNom() + " monde : " + this.getMonde() + " etat : " + this.getEtat();
    }
}