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
	public static void main(String[] args) throws Throwable {
		Scanner input = new Scanner(System.in);
		int reponse = 0;
		FileReader reader;
		Simulateur simulateur = null;

		do {
			System.out.println("--- Menu ---");
			System.out.println("1/ jouer");
			System.out.println("2/ charger un fichier de description");
			System.out.println("3/ sauver la partie actuelle");
			System.out.println("4/ charger une partie");
			System.out.println("5/ quitter");

			reponse = input.nextInt();

			if (reponse >= 2 && reponse <= 4)
				System.out.println("Quel nom de fichier ?");

			switch (reponse) {
				// Jouer
				case 1:
					simulateur.executerJusquALaFin();
					break;
				
				// Chargement d'un fichier de description
				case 2: 
						try {
							reader = new FileReader(input.next());
							simulateur = new Simulateur(reader);
						}
						catch (FileNotFoundException e) {
							System.out.println("Ce fichier de chargement n'existe pas.");
						}
						catch (IOException e) {
							System.out.println("Erreur de lecture durant le chargement");
						}
					break;
				
				// Sauvegarde de la partie actuelle
				case 3:
					if (simulateur != null) {
						try {
							FileOutputStream fos = new FileOutputStream(input.next());
							simulateur.enregistrer(new ObjectOutputStream(fos));
						}
						catch (FileNotFoundException e) {
							System.out.println("Ce fichier de chargement n'existe pas.");
						}
						catch (IOException e) {
							System.out.println("Erreur de lecture durant le chargement");
						}
					}
					else
						System.out.println("Aucun monde n'a encore été créé");
					break;
				
				// Chargement d'une partie
				case 4:
					try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(input.next()));
						simulateur = new Simulateur(ois);
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
					break;
				default:
					reponse = 5;
			}

		} while(reponse != 5);

		System.out.println("Merci d'avoir joué, tu es un beau gosse !");
	}
}