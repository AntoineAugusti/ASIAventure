package fr.insarouen.asi.prog.asiaventure;

import java.io.StreamTokenizer;
import java.io.Reader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


import fr.insarouen.asi.prog.asiaventure.elements.structure.Piece;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.asi.prog.asiaventure.elements.objets.serrurerie.Clef;
import fr.insarouen.asi.prog.asiaventure.elements.structure.Porte;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.JoueurHumain;
import fr.insarouen.asi.prog.asiaventure.elements.vivants.Vivant;
import fr.insarouen.asi.prog.asiaventure.elements.Executable;
import fr.insarouen.asi.prog.asiaventure.elements.Entite;

public class Simulateur {
	private Monde monde;
	private int dureeDuJeu;
	private int tempsPourPrevenirLaFinDuJeu;
	private ArrayList<ConditionDeFin> conditionsDeFin;

	public Simulateur(Monde monde, int dureeDuJeu, int tempsPourPrevenirLaFinDuJeu, ConditionDeFin... conditions) {
		this.monde = monde;
		this.dureeDuJeu = dureeDuJeu;
		this.tempsPourPrevenirLaFinDuJeu = tempsPourPrevenirLaFinDuJeu;
		for (int i = 0; i < conditions.length ; i++) {
			this.conditionsDeFin.add(conditions[i]);
		}
	}

	public Simulateur(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.monde = (Monde) ois.readObject();
	}

	public void ajouterConditionsDeFin(Collection<ConditionDeFin> conditions) {
		this.conditionsDeFin.addAll(conditions);
	}

	public void ajouterConditionDeFin(ConditionDeFin condition) {
		this.conditionsDeFin.add(condition);
	}

	public EtatDuJeu executerUnTour() throws Throwable {
		HashMap <String, Entite> entites = this.monde.getEntites();
		Scanner input = new Scanner(System.in);

		for (Iterator <Entite> i = entites.values().iterator(); i.hasNext();) {
			Entite ent = i.next();
			if (ent instanceof JoueurHumain) {
				JoueurHumain joueur = (JoueurHumain) ent;
				// Afficher la situation
				System.out.println(joueur);
				// Demander l'action à faire
				System.out.println("Que veux-tu faire ?");
				joueur.setOrdre(input.next());
			}
		}

		for (Iterator <Entite> i = entites.values().iterator(); i.hasNext();) {
			Entite ent = i.next();
			if (ent instanceof Executable) {
				Executable executable = (Executable) ent;
				executable.executer();
			}
		}

		return EtatDuJeu.ENCOURS;
	}

	public EtatDuJeu executerNbTours(int n) throws Throwable {
		return EtatDuJeu.ENCOURS;
	}

	public EtatDuJeu executerJusquALaFin() throws Throwable {
		return EtatDuJeu.ENCOURS;
	}

	public void stopperJeu() {

	}

	public Simulateur(Reader reader) throws IOException, NomDEntiteDejaUtiliseDansLeMondeException {
		StreamTokenizer st = new StreamTokenizer(reader);

		int stToken = st.nextToken();
		String mot;

		while (stToken != st.TT_EOF) {
			if (stToken == st.TT_WORD) {
				mot = st.sval;

				// On est sur le premier mot d'une ligne
				st.nextToken();
				switch (mot) {
					case "Monde":
						creerMonde(st);
						break;
					case "Piece":
						creerPiece(st);
						break;
					case "PorteSerrure":
						creerPorteSerrure(st);
						break;
					case "Porte":
						creerPorte(st);
						break;
					case "Clef":
						creerClef(st);
						break;
					case "JoueurHumain":
						creerJoueurHumain(st);
						break;
					case "ConditionDeFinVivantDansPiece":
						creerConditionDeFinVivantDansPiece(st);
						break;
				}
			}

			stToken = st.nextToken();
		}
	}

	public void enregistrer(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.monde);
		oos.writeObject(this.conditionsDeFin);
	}

	private void creerMonde(StreamTokenizer st) throws IOException {
		// Pour une ligne du style:  Monde "Le Monde impitoyable d’ASI"
		this.monde = new Monde(st.sval);
	}

	private void creerPiece(StreamTokenizer st) throws IOException, NomDEntiteDejaUtiliseDansLeMondeException {
		// Pour une ligne du style : Piece "BureauDesNicolas"
		new Piece(st.sval, this.monde);
	}

	private void creerConditionDeFinVivantDansPiece(StreamTokenizer st) throws IOException {
		// Pour une ligne du style : ConditionDeFinVivantDansPiece SUCCES "Etudiant1" "BureauDesNicolas"
		String condition = st.sval;
		st.nextToken();
		String nomVivant = st.sval;
		st.nextToken();
		String nomPiece = st.sval;

		this.ajouterConditionDeFin(new ConditionDeFinVivantDansPiece(EtatDuJeu.valueOf(condition), (Vivant)this.monde.getEntite(nomVivant), (Piece)this.monde.getEntite(nomPiece)));
	}

	private void creerPorteSerrure(StreamTokenizer st) throws IOException, NomDEntiteDejaUtiliseDansLeMondeException {
		// Pour une ligne du style : PorteSerrure "Porte1" "Couloir" "BureauDesNicolas"

		String nomPorte = st.sval;
		st.nextToken();
		String nomPieceA = st.sval;
		st.nextToken();
		String nomPieceB = st.sval;

		// On crée la porte verrouillée
		new Porte(nomPorte, new Serrure(this.monde), this.monde, (Piece)this.monde.getEntite(nomPieceA), (Piece) this.monde.getEntite(nomPieceB));
	}

	private void creerPorte(StreamTokenizer st) throws IOException, NomDEntiteDejaUtiliseDansLeMondeException {
		// Pour une ligne du style : PorteSerrure "Porte1" "Couloir" "BureauDesNicolas"
		
		String nomPorte = st.sval;
		st.nextToken();
		String nomPieceA = st.sval;
		st.nextToken();
		String nomPieceB = st.sval;

		// On crée la porte
		new Porte(nomPorte, this.monde, (Piece)this.monde.getEntite(nomPieceA), (Piece) this.monde.getEntite(nomPieceB));
	}

	private void creerClef(StreamTokenizer st) throws IOException, NomDEntiteDejaUtiliseDansLeMondeException {

		String nomPorte = st.sval;
		Porte porte = (Porte)this.monde.getEntite(nomPorte);
		st.nextToken();
		String nomPieceADeposer = st.sval;
		Piece piece = (Piece)this.monde.getEntite(nomPieceADeposer);

		Clef cle = porte.getSerrure().creerClef();
		piece.deposer(cle);
	}

	private void creerJoueurHumain(StreamTokenizer st) throws IOException, NomDEntiteDejaUtiliseDansLeMondeException {

		String nomJoueurHumain = st.sval;
		st.nextToken();
		int pointVie = (int)st.nval;
		st.nextToken();
		int pointForce = (int)st.nval;
		st.nextToken();
		String nomPiece = st.sval;
		Piece piece = (Piece)this.monde.getEntite(nomPiece);

		// On crée le joueur humain
		new JoueurHumain(nomJoueurHumain, this.monde, pointVie, pointForce, piece);
	}
}