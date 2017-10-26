package com.excilys.project.database.IHM;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.project.database.DAO.DatabaseExchange;
import com.excilys.project.database.controller.DataControl;
import com.excilys.project.database.dataType.Computer;
import com.excilys.project.database.service.Actions;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Bonjour utilisateur");
		System.out.println("Veuillez attendre la connexion au serveur...");
		// ******************* CONNEXION A BDD SQL A FAIRE*************************

		//String qui nous donnera l'autorisation de quitter le programme
		String endOfSession = "";
		while (!endOfSession.equals("quit")) {
			boolean repeat=true;
			System.out.println("*******************************************************************");
			//Connection a la database et query sur les tables "computer" et "company"
			Connection conn = DatabaseExchange.databaseConnection();
			ResultSet rsCompany= DatabaseExchange.databaseGetCompany(conn);
			ResultSet rsComputer= DatabaseExchange.databaseGetComputer(conn);

			System.out.println("Que voulez vous faire : ");
			System.out.println("1. Ajouter un ordinateur");
			System.out.println("2. Modifier un ordinateur");
			System.out.println("3. Voir la liste des ordinateurs");
			System.out.println("4. Voir un ordinateur");
			System.out.println("5. Supprimer un ordinateur");
			System.out.println("6. Quitter le menu");

			//String pour nous donner la direction du switch
			String choiceNumber = sc.nextLine();

			switch (choiceNumber) {
			//Création d'un ordinateur
			case "1":
				while(repeat){
					repeat = Actions.addComputer(conn, sc, rsCompany, rsComputer);					
				}
				break;
				//Edition d'un ordinateur
			case "2":
				while(repeat){
					repeat = Actions.editComputer(conn, sc, rsComputer, rsCompany);					
				}
				break;
				//Retour de la liste des ordinateurs
			case "3":
				Actions.viewAllComputers(conn, sc, rsComputer);
				break;
				//Voir UN ordinateur
			case "4":
				while(repeat){
					repeat = Actions.viewOneComputer(sc, conn, rsComputer);
				}

				break;
				//Supprimer un ordinateur
			case "5":
				while(repeat){
					repeat= Actions.deleteComputer(sc, conn, rsComputer);
				}

				break;
				//Finir la session
			case "6":
				endOfSession = Actions.exitProgram(sc, conn, rsComputer, rsCompany);
				break;

			default:
				System.out.println("erreur de choix");
				break;
			}
		}
	}
}
