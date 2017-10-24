package com.excilys.project.database.datamodifier;
import com.excilys.project.database.dataType.*;

import java.sql.Date;
import java.util.*;
import com.excilys.project.database.IHM.*;


public class ComputerEditor {
	public static void editComputer(Computer computerToEdit, String newName, String newManufacturer, Date newdateIntroduced,
			Date newdateDiscontinued) {
		computerToEdit.computerManufacturer = newManufacturer;
		computerToEdit.computerName = newName;
		computerToEdit.dateIntroduced = newdateIntroduced;
		computerToEdit.dateDiscontinued=newdateDiscontinued;		
	}
	
}

