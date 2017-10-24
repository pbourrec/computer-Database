package com.excilys.project.database.dataType;
//import java.util.Date;
import java.sql.*;


public class Computer {
	
	
	public int id;
	public  String computerName;
	public String computerManufacturer;
	
	public Date dateIntroduced;
	public Date dateDiscontinued;
	
	public String getComputerManufacturer() {
		return computerManufacturer;
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
	public void setComputerManufacturer(String computerManufacturer) {
		this.computerManufacturer = computerManufacturer;
	}

	public Computer(String computerName) {
		super();
		this.computerName = computerName;
	}

	public Computer(String computerName, String computerManufacturer, Date dateIntroduced,
			Date dateDiscontinued) {
		super();
		this.computerName = computerName;
		this.computerManufacturer = computerManufacturer;
		this.dateIntroduced = dateIntroduced;
		this.dateDiscontinued = dateDiscontinued;
	}

	public Computer(String computerName, String computerManufacturer) {
		this.computerName = computerName;
		this.computerManufacturer = computerManufacturer;
	}
	
		
	

}
