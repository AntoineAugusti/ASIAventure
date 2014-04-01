package fr.insarouen.asi.prog.asiaventure;

public class ConditionDeFinConjonctionDeConditionDeFin extends ConditionDeFin {
	private ConditionDeFin[] conditions;

	public ConditionDeFinConjonctionDeConditionDeFin(EtatDuJeu etatConditionVerifiee, ConditionDeFin... conditions) {
		super(etatConditionVerifiee);
		this.conditions = conditions;
	}

	public EtatDuJeu verifierCondition() {
		// On vérifie que le vivant possède tous les objets
		for (int i=0; i < this.conditions.length; i++) {
			if (this.conditions[i].getEtatConditionVerifiee() == EtatDuJeu.ENCOURS)
				return EtatDuJeu.ENCOURS;
		}
		
		return this.getEtatConditionVerifiee();
	}
}