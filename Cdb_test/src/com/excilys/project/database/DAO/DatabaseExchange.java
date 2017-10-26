package com.excilys.project.database.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.project.database.controller.DataControl;
import com.excilys.project.database.dataType.Computer;
import com.mysql.cj.jdbc.Driver;

public class DatabaseExchange {
	
	public static final String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	public static final String username = "root";
	public static final String password = "root";
	
	//connection à la database
	public static Connection databaseConnection() {
		try {
			DriverManager.registerDriver(new Driver());
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage() + " Error sql connection");
		}
	}
	
	//Création d'un statement
	public static Statement databaseStatement(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;

	}

	/**
	 * 
	 * @param conn Connection 
	 * @param computer Nouvel ordinateur à ajouter a la database
	 */
	public static void databaseUpload(Connection conn, Computer computer) {
		//Formattage de la date pour qu'elle soit dans le bon format
		String dateIntroduced = DataControl.formatDate(computer.dateIntroduced);
		String dateDiscontinued = DataControl.formatDate(computer.dateDiscontinued);
		//Try with ressource avec création d'un statement qui sera close a la fin du try
		try (Statement stmt = DatabaseExchange.databaseStatement(conn)) {
			//Creation de la string a utiliser dans la query
			String sqlString = "INSERT into computer (name,introduced, discontinued, company_id)"
					+ "VALUES ("
					+"'" + computer.computerName+"'"
					+ ","
					+ dateIntroduced
					+ ","
					+ dateDiscontinued
					+ "," + computer.ManufacturerID + ")";
			System.out.println("Ordinateur bien rajouté à la base ! :3");
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param conn Connection 
	 * @param newComputer Computer a updater
	 * @param idComputer ID du de l'ordinateur a updater
	 */
	public static void databaseUpdate(Connection conn, Computer newComputer, Long idComputer){
		//formattage des dates
		String dateIntroduced = DataControl.formatDate(newComputer.dateIntroduced);
		String dateDiscontinued = DataControl.formatDate(newComputer.dateDiscontinued);
		
		//Try with ressource avec création d'un statement qui sera close a la fin du try
		try (Statement stmt = DatabaseExchange.databaseStatement(conn)) {
			
			String sqlString = "UPDATE computer SET "
					+"name = '" + newComputer.computerName+"'"
					+ ","
					+ "introduced =" + dateIntroduced
					+ ","
					+ "discontinued = " + dateDiscontinued
					+ ", company_id =" + newComputer.ManufacturerID 
					+ " WHERE id="+idComputer;
			System.out.println("Ordinateur bien rajouté à la base ! :3");
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * @param conn Connection 
	 * @param idComputer ID de l'ordinateur a supprimer
	 */
	public static void databaseDelete(Connection conn, Long idComputer) {
		
		//Try with ressource avec création d'un statement qui sera close a la fin du try
		try (Statement stmt = DatabaseExchange.databaseStatement(conn)) {
			String sqlString = "DELETE  FROM computer WHERE id = " + "'"+idComputer +"'";
			System.out.println("Ordinateur bien Supprimé de la base ! :X");
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @param conn Connection
	 * @return ResultSet rs Resultat de la query sur la table "company"
	 */
	public static ResultSet databaseGetCompany(Connection conn) {
		ResultSet rs = null;
		Statement stmt = DatabaseExchange.databaseStatement(conn);
		try {
			rs = stmt.executeQuery("SELECT * FROM company");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}
	
	/**
	 * 
	 * @param conn Connection
	 * @return ResultSet rs Resultat de la query sur la table "computer"
	 */
	public static ResultSet databaseGetComputer(Connection conn) {
		ResultSet rs = null;
		Statement stmt = DatabaseExchange.databaseStatement(conn);
		try {
			rs = stmt.executeQuery("SELECT * FROM computer");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}

	
	/**
	 * 
	 * @param conn Connection
	 * @param computerID Id de l'ordinateur a rechercher
	 * @return ResultSet rs resultat de la query sur la table 'computer avec l'ID voulu
	 */
	public static ResultSet databaseQueryOne(Connection conn, Long computerID){
		ResultSet rs = null;
		Statement stmt = DatabaseExchange.databaseStatement(conn);
		try {
			String queryString = "SELECT * FROM computer WHERE id=" +"'"+ computerID+"'"; 
			rs = stmt.executeQuery(queryString);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}

	
	/**
	 * 
	 * @param conn Connection
	 * @returnint sizeTable Taille de la table "computer"
	 */
	public static int databaseGetSizeComputer(Connection conn) {
		ResultSet rs = null;
		Statement stmt = DatabaseExchange.databaseStatement(conn);
		int sizeTable = 0;
		try {
			rs = stmt.executeQuery("SELECT COUNT(*) FROM computer");
			rs.next();
			sizeTable = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sizeTable;
		
	}
	
	/**
	 * 
	 * @param stmt Statement
	 * @param computerManufacturer ID du fabricant voulu
	 * @return boolean isOK le boolean sera vrai si la table "company" contient bien l'ID du fabricant voulu
	 */
	public static boolean isIdOkCompany(Statement stmt, String computerManufacturer){
		boolean isOK = false;
		// si computerManufacturer est null, on considère que l'utilisateur ne veut pas déclarer le fabricant de l'ordinateur
		if(!computerManufacturer.equals("")){
		try {
			//On regarde la TAILLE de la query. Si on a 1 résultat, alors l'ID est bon
			ResultSet rs= stmt.executeQuery("SELECT COUNT(*) from company WHERE ID="+Long.valueOf(computerManufacturer));
			rs.next();
			if(rs.getLong(1)==1){
				isOK = true;
			}else {
				System.out.println("l'ID rentré n'existe pas, veuillez recommencer");
			}

		} catch (NumberFormatException e1) {
			System.out.println("l'ID rentré n'est pas sous le bon format, veuillez recommencer");
		} catch ( SQLException e2) {
			e2.printStackTrace();
		}
		} else{
			isOK=true;
		}
		return isOK;
	}
	
	/**
	 * 
	 * @param stmt Statement
	 * @param computer ID ID de l'ordinateur voulu
	 * @return boolean isOK le boolean sera vrai si la table "computer" contient bien l'ID de l'ordinateur voulu
	 */
	public static boolean isIdOkComputer(Statement stmt, String computerId){
		boolean isOK = false;
		try {
			//On regarde la TAILLE de la query. Si on a 1 résultat, alors l'ID est bon
			ResultSet rs= stmt.executeQuery("SELECT COUNT(*) from computer WHERE ID="+Long.valueOf(computerId));
			rs.next();
			if(rs.getLong(1)==1){
				isOK = true;
			}else {
				System.out.println("l'ID rentré n'existe pas, veuillez recommencer");
			}
		} catch (NumberFormatException e1) {
			System.out.println("l'ID rentré n'est pas sous le bon format, veuillez recommencer");
		} catch ( SQLException e2) {
			System.out.println("l'ID rentré n'existe pas, veuillez recommencer");
		}
		return isOK;
	}
}
