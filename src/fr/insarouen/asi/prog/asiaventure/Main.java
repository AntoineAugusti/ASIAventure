package fr.insarouen.asi.prog.asiaventure;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	private static Scanner input = new Scanner(System.in);
	private static Simulateur simulateur = null;
	private static EtatDuJeu etat;
	private static String continuerJeu = "O";

	public static void main(String[] args) throws Throwable {
		int reponse = 0;
		
		do {
			System.out.println("--- Menu ---");
			System.out.println("1/ Jouer");
			System.out.println("2/ Charger un fichier de description");
			System.out.println("3/ Sauver la partie actuelle");
			System.out.println("4/ Charger une partie");
			System.out.println("5/ Quitter");

			reponse = Main.input.nextInt();

			if (reponse >= 2 && reponse <= 4)
				System.out.println("Quel nom de fichier ?");

			switch (reponse) {
				// Jouer
				case 1: jouer(); break;
				
				// Chargement d'un fichier de description
				case 2:  chargerFichierDescription(); break;
				
				// Sauvegarde de la partie actuelle
				case 3: sauvegarderPartie(); break;
				
				// Chargement d'une partie
				case 4: chargerPartie(); break;

				// Quitter
				default:
					reponse = 5;
			}

		} while(reponse != 5);

		System.out.println("Merci d'avoir joué, tu es un beau gosse !");
	}

	private static void jouer() throws Throwable {
		if (Main.simulateur != null) {
			do {
				Main.etat = Main.simulateur.executerUnTour();
				if (Main.etat == EtatDuJeu.SUCCES)
					System.out.println("Bravo beau gosse, tu as gagné !");
				else if (Main.etat == EtatDuJeu.ECHEC)
					System.out.println("Tu as perdu. Tu peux te suicider.");
				else {
					System.out.println("Continuer à jouer ? (O/N)");
					Main.continuerJeu = Main.input.next().toUpperCase();
				}

			} while (Main.continuerJeu.equals("O") && Main.etat == EtatDuJeu.ENCOURS);
		}
		else
			System.out.println("Faudrait peut-être charger le jeu avant !");
	}

	private static void chargerFichierDescription() throws NomDEntiteDejaUtiliseDansLeMondeException {
		FileReader reader;

		try {
			reader = new FileReader(Main.input.next());
			Main.simulateur = new Simulateur(reader);
		}
		catch (FileNotFoundException e) {
			System.out.println("Ce fichier de chargement n'existe pas.");
		}
		catch (IOException e) {
			System.out.println("Erreur de lecture durant le chargement");
		}
	}

	private static void sauvegarderPartie() {
		if (Main.simulateur != null) {
			try {
				FileOutputStream fos = new FileOutputStream(Main.input.next());
				Main.simulateur.enregistrer(new ObjectOutputStream(fos));
				fos.close();
			}
			catch (FileNotFoundException e) {
				System.out.println("Ce fichier de chargement n'existe pas.");
			}
			catch (IOException e) {
				System.out.println("Erreur de lecture durant le chargement");
			}
		}
		else
			System.out.println("Aucun monde n'a encore été créé. Tu ne peux rien sauvegarder !");
	}

	private static void chargerPartie() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Main.input.next()));
			Main.simulateur = new Simulateur(ois);
			ois.close();
			
			System.out.println("La sauvergarde a été chargée avec succès.");
		}
		catch (FileNotFoundException e) {
			System.out.println("Ce fichier de sauvegarde n'existe pas.");
		}
		catch (IOException e) {
			System.out.println("Erreur de lecture durant le chargement");
		}
		catch (ClassNotFoundException e) {
			System.out.println("Le fichier n'est pas valide");
		}
	}
}