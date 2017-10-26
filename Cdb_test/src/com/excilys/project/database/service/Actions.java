package com.excilys.project.database.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.stream.events.EndDocument;

import com.excilys.project.database.DAO.DatabaseExchange;
import com.excilys.project.database.controller.DataControl;
import com.excilys.project.database.dataType.Computer;
import com.mysql.cj.api.mysqla.result.Resultset;

public class Actions {
	/**
	 * 
	 * @param conn Connection intialisée au début
	 * @param sc Scanner d'entrée utilisateur
	 * @param rsCompany ResultSet de la query à la table "companies"
	 * @param rsComputer ResultSet de la query à la table "computer"
	 * @return boolean repeat doit on enter à nouveau un ordinateur
	 */
	public static boolean addComputer(Connection conn,Scanner sc, ResultSet rsCompany, ResultSet rsComputer){
		boolean repeat=false;
		System.out.println("*******************************************************************");
		System.out.println("Quel sera le nom de l'ordinateur");
		String computerName = sc.nextLine();
		while(computerName.isEmpty()){
			System.out.println("Le nom de l'ordinateur ne peut être vide, merci de remplir ce champ");
			computerName=sc.nextLine();
		}
		//Affichage de la liste des fabricants
		List<Long> countCompany = new ArrayList();
		try {
			int countligne = 0;
			while(rsCompany.next()){

				System.out.print(rsCompany.getInt(1)+" =>");
				countCompany.add(rsCompany.getLong(1));
				System.out.println(rsCompany.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Quel sera le constructeur de l'ordinateur (choisir un ID)" );

		String computerManufacturer = sc.nextLine();
		Long idCompany = DataControl.stringToLongIDCompany(conn, computerManufacturer, sc);


		System.out.println("Date de mise en service de l'ordinateur (format dd/MM/yyyy)");
		Date dateStart = null;
		while(dateStart==null) {
			String computerStartingDate = sc.nextLine();
			//Condition sur le remplissage du champ "date", l'utilisateur peut le laisser vide
			if(!computerStartingDate.equals("")){
				dateStart= DataControl.convertStringToTimestamp(computerStartingDate);
			}else{break;}
		}
		System.out.println("Date de mise en retraite de l'ordinateur (format dd/MM/yyyy)");

		Date dateEnd= null;
		while(dateEnd==null) {
			String computerEndDate = sc.nextLine();
			//Condition sur le remplissage du champ "date", l'utilisateur peut le laisser vide
			if(!computerEndDate.equals("")){
				dateEnd= DataControl.convertStringToTimestamp(computerEndDate);
			}else {break;}
		}

		// *******************
		Computer newComputer = new Computer(computerName,idCompany, dateStart, dateEnd);

		System.out.println("Voulez vous bien créer l'ordinateur suivant : Y/N");
		System.out.println("Nom : " + newComputer.computerName);
		System.out.println("Nom du constructeur : "+ newComputer.ManufacturerID);
		System.out.println("Mise en service: "+ newComputer.dateIntroduced);
		System.out.println("Mise en retraite: "+ newComputer.dateDiscontinued);
		String choice = sc.nextLine();
		//errorTypo empechera l'utilisateur de quitter l'ajout d'ordinateur s'il fait une faute ou une typo
		boolean errorTypo=false;
		while(!errorTypo){
			if (choice.equals("Y")) {
				DatabaseExchange.databaseUpload(conn, newComputer);
				System.out.println("Voulez vous en rajouter un autre? Y/N");
				choice=sc.nextLine();
				//l'utilisateur a créé un ordi, veut il en rajouter un autre ? 
				if(choice.equals("Y") || choice.equals("y")){
					repeat=true;
					errorTypo=true;
				} else{
					repeat = false;
					errorTypo= true;
					System.out.println("Retour au menu principal...");
				}

			} else if(choice.equals("N") || choice.equals("n")) {
				System.out.println("Abandon de l'ajout...");
				//l'utilisateur a fait une erreur dans la création de son ordinateur, il peut recommencer la procédure
				System.out.println("Voulez vous en ajouter un autre ? Y/N");
				choice=sc.nextLine();
				if(choice.equals("Y") || choice.equals("y")){
					errorTypo=true;
					repeat=true;
				} else{
					repeat = false;
					errorTypo= true;
					System.out.println("Retour au menu principal...");
				}
			} else{
				errorTypo=false;
			}
		}
		return repeat;
	}

	/**
	 * @param conn Connection intialisée au début
	 * @param sc Scanner d'entrée utilisateur
	 * @param rsCompany ResultSet de la query à la table "companies"
	 * @param rsComputer ResultSet de la query à la table "computer"
	 * @return
	 */
	public static boolean editComputer (Connection conn, Scanner sc, ResultSet rsComputer, ResultSet rsCompany){
		//Affichage de tous les ordinateurs
		System.out.println("Voulez vous voir la liste des ordinateurs ? Y/N");
		String seeList = sc.nextLine();
		if (seeList.equals("Y") || seeList.equals("y")){
			viewAllComputers(conn, sc, rsComputer);
		}

		//controle validité de l'ID
		String computerID = sc.nextLine();
		if(computerID.equals("exit") || computerID.equals("quit")){return false;}

		Long idComputer = DataControl.stringToLongIDCompany(conn,computerID, sc);

		try {
			ResultSet computerToEdit = DatabaseExchange.databaseQueryOne(conn, idComputer);
			while(computerToEdit.next()){
				System.out.println("Modification de cet ordinateur ");
				System.out.print(computerToEdit.getLong(1)+" =>");
				System.out.print(computerToEdit.getString(2)+"     ");
				System.out.print(computerToEdit.getDate(3)+"     ");
				System.out.print(computerToEdit.getDate(4)+"     ");
				System.out.println(computerToEdit.getInt(5)+"     ");
			}
			System.out.println("Nouveau nom ?");
			String computerNewName = sc.nextLine();
			while(computerNewName.isEmpty()){
				System.out.println("Le nom de l'ordinateur ne peut être vide, merci de remplir ce champ");
				computerNewName=sc.nextLine();
			}
			try {
				//affichage des fabricants
				while(rsCompany.next()){

					System.out.print(rsCompany.getInt(1)+" =>");
					System.out.println(rsCompany.getString(2));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Quel sera le nouveau constructeur de l'ordinateur (choisir un ID)" );
			String computerNewManufacturer = sc.nextLine();
			Long idNewCompany = DataControl.stringToLongIDCompany(conn,computerNewManufacturer, sc);


			System.out.println("Nouvelle Date de mise en service de l'ordinateur (format dd/MM/yyyy)");
			Date newDateStart = null;
			while(newDateStart==null) {
				String computerNewStartingDate = sc.nextLine();

				if(!computerNewStartingDate.equals("")){

					newDateStart= DataControl.convertStringToTimestamp(computerNewStartingDate);
				}else{break;}
			}
			
			System.out.println("Nouvelle Date de mise en retraite de l'ordinateur (format dd/MM/yyyy)");
			Date newDateEnd= null;
			while(newDateEnd==null) {
				String computerNewEndDate = sc.nextLine();

				if (!computerNewEndDate.equals("")){

					newDateEnd= DataControl.convertStringToTimestamp(computerNewEndDate);
				}else {break;}
			}
			// Creation d'un nouvel ordinateur et confirmation des données
			Computer newComputerUpdate = new Computer(computerNewName, idNewCompany, newDateStart, newDateEnd);
			System.out.println("Voulez vous bien créer l'ordinateur suivant : Y/N");
			System.out.println("Nom : " + newComputerUpdate.computerName);
			System.out.println("Nom du constructeur : "+ newComputerUpdate.ManufacturerID);
			System.out.println("Mise en service: "+ newComputerUpdate.dateIntroduced);
			System.out.println("Mise en retraite: "+ newComputerUpdate.dateDiscontinued);
			String updateComputer = sc.nextLine();
			if(updateComputer.equals("Y")|| updateComputer.equals("y")){
			DatabaseExchange.databaseUpdate(conn, newComputerUpdate, idComputer);
			} else {
				System.out.println("Abandon de la modification");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

	
	
	/**
	 * 
	 * @param conn Connection établie au début
	 * @param sc Scanner input
	 * @param rsComputer Resultat de la query
	 */
	public static void viewAllComputers(Connection conn,Scanner sc, ResultSet rsComputer ){
		try {
			//Demande du nombre d'ordinateurs par "page"
			System.out.println("Combien d'ordinateurs voulez vous afficher à la suite ? ");
			String howManyToPrint = sc.nextLine();
			Integer howManyLines = DataControl.stringToInt(howManyToPrint, sc);
			//taille de la database
			int sizeTable = DatabaseExchange.databaseGetSizeComputer(conn);
			//boucle pour parcourir la database
			for (int i=1; i<sizeTable+1; i++){
				//Tant qu'il y a des ordinateurs dans la database et que l'on n'a pas atteint le nombre d'ordinateurs par page
				while(rsComputer.next() && i%howManyLines!=0){
					i++;
					System.out.print(rsComputer.getLong(1)+" =>");
					System.out.print(rsComputer.getString(2)+"     ");
					System.out.print(rsComputer.getDate(3)+"     ");
					System.out.print(rsComputer.getDate(4)+"     ");
					System.out.println(rsComputer.getInt(5)+"     ");
				}
				System.out.println("previous/ next ?");
				String navigate = sc.nextLine();
				//on revient a la page précédente
				if(navigate.equals("previous")){
					i-=howManyLines;
					for(int j=0; j< 2*howManyLines; j++){
						rsComputer.previous();
					}
				}else if (navigate.equals("next")){

				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	/**
	 * 	
	 * @param sc Scanner input
	 * @param conn Connection établie au débubt
	 * @param rsComputer Resultat de la query sur la table "computer"
	 * @return
	 */
	public static boolean viewOneComputer (Scanner sc,Connection conn,ResultSet rsComputer){
		boolean repeat;
		
		System.out.println("Quel ordinateur voulez vous voir ? Entrez l'ID");
		String computerIDToSee = sc.nextLine();
		if(computerIDToSee.equals("exit") || computerIDToSee.equals("quit")){return repeat =false;}
		Long idComputerToSee = DataControl.stringToLongIDComputer(conn,computerIDToSee, sc);

		//Recherche d'UN ordinateur
		ResultSet computerToShow = DatabaseExchange.databaseQueryOne(conn, idComputerToSee);
		try {
			while(computerToShow.next()){
				System.out.print(computerToShow.getLong(1)+" =>");
				System.out.print(computerToShow.getString(2)+"    ");
				System.out.print(computerToShow.getDate(3)+"     ");
				System.out.print(computerToShow.getDate(4)+"     ");
				System.out.println(computerToShow.getInt(5)+"     ");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Voulez vous en voir un autre ? Y/N");
		String choice=sc.nextLine();
		if(choice.equals("Y")){
			repeat=true;
		} else{
			repeat = false;
		} 
		return repeat;
	}

	
	/**
	 * 
	 * @param sc scanner input clavier
	 * @param conn connection établie au début
	 * @param rsComputer Resultat de la Query sur la table "computer"
	 * @return
	 */
	public static boolean deleteComputer(Scanner sc, Connection conn, ResultSet rsComputer){
		boolean repeat;
		//Affichage de tous les ordinateurs
		System.out.println("Voulez vous voir la liste des ordinateurs ? Y/N");
		String seeList = sc.nextLine();
		if (seeList.equals("Y") || seeList.equals("y")){
			viewAllComputers(conn, sc, rsComputer);
		}

		System.out.println("Quel ordinateur voulez vous supprimer ? Entrez l'ID");

		String computerIDToDelete= sc.nextLine();
		if(computerIDToDelete.equals("exit") || computerIDToDelete.equals("quit")){return repeat =false;}
		Long idComputerToDelete = DataControl.stringToLongIDComputer(conn,computerIDToDelete, sc);
		ResultSet computerToDelete = DatabaseExchange.databaseQueryOne(conn, idComputerToDelete);
		try {
			while(computerToDelete.next()){
				System.out.println("Suppression de cet ordinateur ");
				System.out.print(computerToDelete.getLong(1)+" =>");
				System.out.print(computerToDelete.getString(2)+"     ");
				System.out.print(computerToDelete.getDate(3)+"     ");
				System.out.print(computerToDelete.getDate(4)+"     ");
				System.out.println(computerToDelete.getInt(5)+"     ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Voulez vous supprimer cet ordinateur ?");
		String areYouSure = sc.nextLine();
		if(areYouSure.equals("Y")|| areYouSure.equals("y")){
			DatabaseExchange.databaseDelete(conn, idComputerToDelete);
		} else{
			System.out.println("Abandon de la suppression");
		}
		System.out.println("Voulez vous en supprimer  un autre ? ? Y/N");
		String choice=sc.nextLine();
		if(choice.equals("Y")){
			repeat=true;
		} else{
			repeat = false;
		} 
		return repeat;
	}
	
	
	/**
	 * 
	 * @param sc Scanner input clavier, on le ferme
	 * @param conn Fermeture de la connection
	 * @param rsComputer fermeture de la query sur la table "computer"
	 * @param rsCompany fermeture de la query sur la table "Company"
	 * @return String endOfSession pour quitter la boucle while du main
	 */
	public static String exitProgram (Scanner sc, Connection conn, ResultSet rsComputer, ResultSet rsCompany){
		System.out.println("Quitter la machine ? Y/N");
		String choice = sc.nextLine();
		String endOfSession = "";
		if (choice.equals("Y")) {
			System.out.println("Au revoir utilisateur, a bientot");
			endOfSession = "quit";

		} else if (choice.equals("N")) {
			endOfSession ="Stay";
		}
		//fermeture des variables
		try {
			rsComputer.close();
			rsCompany.close();
			sc.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endOfSession;
	}
}



