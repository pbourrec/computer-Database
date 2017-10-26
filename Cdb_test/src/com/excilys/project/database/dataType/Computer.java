package com.excilys.project.database.dataType;
//import java.util.Date;
import java.sql.*;


public class Computer {
	
	
	public long id;
	public  String computerName;
	public long ManufacturerID;
	
	public Date dateIntroduced;
	public Date dateDiscontinued;
	
	public long getComputerManufacturer() {
		return ManufacturerID;
	}

	public String getComputerName() {
		return computerName;
	}
	public Date getDateIntroduced() {
		return dateIntroduced;
	}
	public Date getDateDiscontinued() {
		return dateDiscontinued;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public void setDateIntroduced(Date dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	public void setDateDiscontinued(Date dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
		
	}
	public void setComputerManufacturer(long computerManufacturer) {
		this.ManufacturerID = computerManufacturer;
	}

	public Computer(String computerName) {
		super();
		this.computerName = computerName;
	}
	
	//constructeur si on a toutes les données
	public Computer(String computerName, long computerManufacturer, Date dateIntroduced,
			Date dateDiscontinued) {
		super();
		this.computerName = computerName;
		this.ManufacturerID = computerManufacturer;
		this.dateIntroduced = dateIntroduced;
		this.dateDiscontinued = dateDiscontinued;
	}
	
	//Constructeur si l'on n'a que le nom et le fabricant
	/**
	 * @deprecated
	 * @param computerName
	 * @param computerManufacturer
	 */
	public Computer(String computerName, long computerManufacturer) {
		this.computerName = computerName;
		this.ManufacturerID = computerManufacturer;
	}
	
		
	

}
