package fr.insarouen.asi.prog.asiaventure;

import java.io.Serializable;

public abstract class ConditionDeFin extends Object implements Serializable {
	private EtatDuJeu etat;

	public ConditionDeFin(EtatDuJeu etatDuJeu)  {
		this.etat = etatDuJeu;
	}

	public EtatDuJeu getEtatConditionVerifiee() {
		return this.etat;
	}

	public abstract EtatDuJeu verifierCondition();
}