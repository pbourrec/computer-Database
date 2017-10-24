package com.excilys.project.database.IHM;

import java.util.Scanner;

import com.excilys.project.database.dataType.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Bonjour utilisateur");
		System.out.println("Veuillez attendre la connexion au serveur...");
		// ******************* CONNEXION A BDD SQL A FAIRE *************************
		String endOfSession = "";
		String choice = "";
		while(!endOfSession.equals("quit")) {

			System.out.println("Que voulez vous faire : ");
			System.out.println("1. Ajouter un ordinateur");
			System.out.println("2. Modifier un ordinateur");
			System.out.println("3. Voir la liste des ordinateurs");
			System.out.println("4. Voir un ordinateur");
			System.out.println("5. Supprimer un ordinateur");
			System.out.println("6. Quitter le menu");

			String choiceNumber;

			choiceNumber = sc.nextLine();

			switch (choiceNumber) {
			case "1":
				System.out.println("*******************************************************************");
				System.out.println("Quel sera le nom de l'ordinateur");
				String computerName = sc.nextLine();

				System.out.println("Quel sera le constructeur de l'ordinateur");
				String computerManufacturer= sc.nextLine();

				System.out.println("Date de mise en service de l'ordinateur");
				String computerStartingDate = sc.nextLine();
				
				System.out.println("Date de mise en retraite de l'ordinateur");
				String computerEndDate = sc.nextLine();

				//			Computer newComputer = new Computer(computerName, computerManufacturer,(date) computerStartingDate,(date) computerEndDate);

				//************   TEMPORAIRE TANT QUE DATE PAS REGLE *******************
				Computer newComputer = new Computer(computerName, computerManufacturer);

				System.out.println("Voulez vous bien créer l'ordinateur suivant : Y/N");
				System.out.println("Nom : " + newComputer.computerName);
				System.out.println("Nom du constructeur : "  + newComputer.computerManufacturer);
				choice = sc.nextLine();
				if (choice.equals("Y")) {
					//********************* Rajouter Computer a la database ******************

				} else {
					// *************** Supprimer le newcomputer **************
				}
				break;
			case "2":
				System.out.println("Fonctionnalité non disponible pour le moment");
				break;
			case "3":
				System.out.println("Fonctionnalité non disponible pour le moment");
				break;
			case "4":
				System.out.println("Fonctionnalité non disponible pour le moment");
				break;
			case "5":
				System.out.println("Fonctionnalité non disponible pour le moment");
				break;

			case "6":
				System.out.println("Quitter la machine ? Y/N");
				choice = sc.nextLine();
				if (choice.equals("Y")) {
					System.out.println("Au revoir utilisateur, a bientot");
					endOfSession="quit";

				} else if(choice.equals("N")) {
				}
				break;

			default : 
				System.out.println("erreur de choix");
				break;

			}


		}
	}

}
