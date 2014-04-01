package fr.insarouen.asi.prog.asiaventure;

import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;

public class ConditionDeFinVivantDansPieceEtPossedeObjets extends ConditionDeFin {
	private Vivant vivant;
	private Objet[] objets;
	private Piece piece;

	public ConditionDeFinVivantDansPieceEtPossedeObjets(EtatDuJeu etatConditionVerifiee, Vivant vivant, Piece piece, Objet... objets) {
		super(etatConditionVerifiee);
		this.piece = piece;
		this.vivant = vivant;
		this.objets = objets;
	}

	public EtatDuJeu verifierCondition() {
		ConditionDeFinVivantPossedeObjets c1 = new ConditionDeFinVivantPossedeObjets(this.getEtatConditionVerifiee(), this.vivant, this.objets);
		ConditionDeFinVivantDansPiece c2 = new ConditionDeFinVivantDansPiece(this.getEtatConditionVerifiee(), this.vivant, this.piece);
		
		if (c1.verifierCondition() != EtatDuJeu.ENCOURS && c2.verifierCondition() != EtatDuJeu.ENCOURS)
			return this.getEtatConditionVerifiee();
		else
			return EtatDuJeu.ENCOURS;
	}
}