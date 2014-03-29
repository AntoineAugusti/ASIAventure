package fr.insarouen.asi.prog.asiaventure;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

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
				System.out.println("Quel nom de fichier ?\n");

			switch (reponse) {
				case 2: 
					reader = new FileReader(input.next());
					simulateur = new Simulateur(reader);
					break;
				case 3:
					FileOutputStream fos = new FileOutputStream(input.next());
					simulateur.enregistrer(new ObjectOutputStream(fos));
					break;
				case 4:
					FileInputStream fis = new FileInputStream(input.next());
					simulateur = new Simulateur(new ObjectInputStream(fis));
					break;
				default:
					reponse = 5;
			}

		} while(reponse != 5);
	}
}