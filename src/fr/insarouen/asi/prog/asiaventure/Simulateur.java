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
	private Monde monde = null;
	private int dureeDuJeu;
	private int tempsPourPrevenirLaFinDuJeu;
	private ArrayList<ConditionDeFin> conditionsDeFin = new ArrayList<ConditionDeFin>();

	/**
	 * @brief Crée un simulateur
	 * 
	 * @param monde Le monde dans lequel on veut jouer
	 * @param dureeDuJeu La durée du jeu
	 * @param tempsPourPrevenirLaFinDuJeu Le temps auquel on limite le jeu
	 * @param conditions Les conditions de fin du jeu
	 */
	public Simulateur(Monde monde, int dureeDuJeu, int tempsPourPrevenirLaFinDuJeu, ConditionDeFin... conditions) {
		this.monde = monde;
		this.dureeDuJeu = dureeDuJeu;
		this.tempsPourPrevenirLaFinDuJeu = tempsPourPrevenirLaFinDuJeu;
		for (int i = 0; i < conditions.length ; i++) {
			this.conditionsDeFin.add(conditions[i]);
		}
	}

	/**
	 * @brief Charge une partie depuis une dernière sauvegarde
	 * 
	 * @param ois L'OIS à charger
	 */
	public Simulateur(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.monde = (Monde) ois.readObject();
	}

	/**
	 * @brief Ajoute plusieurs conditions de fin
	 * 
	 * @param conditions Les conditions de fin
	 */
	public void ajouterConditionsDeFin(Collection<ConditionDeFin> conditions) {
		this.conditionsDeFin.addAll(conditions);
	}

	/**
	 * @brief Ajoute une condition de fin
	 * 
	 * @param condition La condition de fin
	 */
	public void ajouterConditionDeFin(ConditionDeFin condition) {
		this.conditionsDeFin.add(condition);
	}

	/**
	 * @brief Joue un tour
	 * @details Demande aux joueurs humains ce qu'ils veulent faire puis joue toutes les personnes qui peuvent
	 * @return L'état du jeu après le tour de jeu
	 */
	public EtatDuJeu executerUnTour() throws Throwable {
		HashMap <String, Entite> entites = this.monde.getEntites();
		Scanner input = new Scanner(System.in);

		// On demande à tous les joueurs humains ce qu'ils veulent faire 
		for (Iterator <Entite> i = entites.values().iterator(); i.hasNext();) {
			Entite ent = i.next();
			if (ent instanceof JoueurHumain) {
				JoueurHumain joueur = (JoueurHumain) ent;
				// Afficher la situation
				System.out.println(joueur);
				// Demander l'action à faire
				System.out.println("Que veux-tu faire " +joueur.getNom()+ " ?");
				joueur.setOrdre(input.nextLine());
			}
		}

		// On "exécute" tous les exécutables 
		for (Iterator <Entite> i = entites.values().iterator(); i.hasNext();) {
			Entite ent = i.next();
			if (ent instanceof Executable) {
				Executable executable = (Executable) ent;
				executable.executer();
			}
		}

		// On vérifie les conditions de fin. Si une condition est vérifie, on la retourne. Si non on dit que le jeu est "en cours"
		for (Iterator <ConditionDeFin> i = this.conditionsDeFin.iterator(); i.hasNext();) {
			ConditionDeFin condition = i.next();
			if (condition.verifierCondition() != EtatDuJeu.ENCOURS)
				return condition.verifierCondition();
		}

		return EtatDuJeu.ENCOURS;
	}

	/**
	 * @brief Joue pendant un nombre de tours donné
	 * 
	 * @param n Le nombre de tours que l'on veut jouer
	 * @return L'état du jeu en fin de partie
	 */
	public EtatDuJeu executerNbTours(int n) throws Throwable {
		EtatDuJeu etat;

		for (int i = 1; i <= n; i++) {
			etat = this.executerUnTour();
			if (etat != EtatDuJeu.ENCOURS)
				return etat;
		}

		return EtatDuJeu.ENCOURS;
	}

	/**
	 * @brief Joue jusqu'à la partie soit gagnée ou perdue
	 * @return L'état du jeu en fin de partie
	 */
	public EtatDuJeu executerJusquALaFin() throws Throwable {
		EtatDuJeu etat;

		do {
			etat = this.executerUnTour();
		} while (etat == EtatDuJeu.ENCOURS);

		return etat;
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

	/**
	 * @brief Enregistre une partie
	 * 
	 * @param oos L'OOS de sortie
	 */
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

		ConditionDeFinVivantDansPiece conditionFin = new ConditionDeFinVivantDansPiece(EtatDuJeu.valueOf(condition), (Vivant)this.monde.getEntite(nomVivant), (Piece)this.monde.getEntite(nomPiece));
		this.ajouterConditionDeFin(conditionFin);
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