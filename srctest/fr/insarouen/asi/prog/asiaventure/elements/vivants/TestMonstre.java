package fr.insarouen.asi.prog.asiaventure.elements.vivants;

import fr.insarouen.asi.prog.asiaventure.Monde;
import fr.insarouen.asi.prog.asiaventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;
import fr.insarouen.asi.prog.asiaventure.elements.Etat;
import fr.insarouen.asi.prog.asiaventure.elements.objets.Objet;
import fr.insarouen.asi.prog.asiaventure.elements.objets.PiedDeBiche;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ActivationException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Porte;
import fr.insarouen.asi.prog.asiaventure.elements.structure.PorteFermeException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.structure.ObjetNonDeplacableDeLaPieceException;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.ObjetNonPossedeParLeVivantException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;


public class TestMonstre {

	Monstre monstre;
    Monde monde;
    Objet objet1, objet2;
	Piece piece1, piece2;
    PiedDeBiche pdb;
    Porte porte;

    @Before
    public void avantTest() throws NomDEntiteDejaUtiliseDansLeMondeException {
        monde = new Monde("Nouveau Monde");
        piece1 = new Piece("mahr215",monde);
        piece2 = new Piece("mahr211",monde);
        porte = new Porte("Heaven's door", monde, piece1, piece2);
        objet1 = new Objet("Souris",monde){
            public boolean estDeplacable() {
                return true;
            }
        };

        objet2 = new Objet("Clavier",monde){
            public boolean estDeplacable() {
                return true;
            }
        };
        piece1.deposer(objet1);
        piece2.deposer(objet2);
        pdb = new PiedDeBiche("PDB", monde);
        monstre = new Monstre("Toto", monde, 2, 0, piece1, pdb);
    }

    @Test
    public void testExecuter() throws ActivationException, PorteFermeException, ObjetAbsentDeLaPieceException, ObjetNonPossedeParLeVivantException, PorteInexistanteDansLaPieceException, ObjetNonDeplacableDeLaPieceException {
    	assertThat(monstre.possede(pdb), is(true));
    	assertThat(monstre.possede(objet1), is(false));
    	assertThat(monstre.possede(objet2), is(false));
    	assertThat(monstre.getPointVie(), is(2));
    	assertThat(monstre.getPiece(), is(piece1));

    	monstre.executer();

    	assertThat(monstre.getPiece(), is(piece2));
    	assertThat(monstre.possede(pdb), is(false));
    	assertThat(monstre.possede(objet1), is(false));
    	assertThat(monstre.getPointVie(), is(1));
        assertThat(monstre.possede(objet2), is(true));
    }
}