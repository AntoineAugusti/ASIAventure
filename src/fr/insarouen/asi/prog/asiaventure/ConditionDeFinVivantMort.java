package fr.insarouen.asi.prog.asiaventure;

import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;

public class ConditionDeFinVivantMort extends ConditionDeFin {
	private Vivant vivant;

	public ConditionDeFinVivantMort(EtatDuJeu etatConditionVerifiee, Vivant vivant) {
		super(etatConditionVerifiee);
		this.vivant = vivant;
	}

	public EtatDuJeu verifierCondition() {
		if (this.vivant.estMort())
			return this.getEtatConditionVerifiee();
		else
			return EtatDuJeu.ENCOURS;
	}
}