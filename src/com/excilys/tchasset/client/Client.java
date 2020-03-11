package com.excilys.tchasset.client;

import java.util.Scanner;

import com.excilys.tchasset.service.CompanyService;
import com.excilys.tchasset.service.ComputerService;

public class Client {
	
	private EnumCli enumCli;
	
	public void affiche() {
		menu();
		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		enumCli = EnumCli.value(option);
		
		switch(enumCli) {
			case LISTALLCOMPANY:
				System.out.println(CompanyService.getInstance().getCompanies().toString());
				break;
			case LISTCOMPANY:
				int id=0;
				//TODO
				CompanyService.getInstance().getById(id);
				break;
			case LISTALLCOMPUTER:
				ComputerService.getInstance().getComputers();
				break;
			case LISTCOMPUTER:
				//TODO
				ComputerService.getInstance().getById(0);
				break;
			case ADDCOMPUTER:
				//TODO 
				ComputerService.getInstance().addComputer(null);
				break;
			case DELETECOMPUTER:
				//TODO
				ComputerService.getInstance().deleteComputer(0);
				break;
			case UPDATECOMPUTER:
				//TODO
				ComputerService.getInstance().updateComputer(null);
				break;
			case EXIT:
				System.out.println("Bye");
				sc.close();
		}
	}
	
	private void menu() {
		System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # #");
		System.out.println("# Entrez le chiffre :                           #");
		System.out.println("# 1 : Pour lister les companies                 #");
		System.out.println("# 2 : Pour afficher une companie                #");
		System.out.println("# 3 : Pour lister tous les ordinateurs          #");
		System.out.println("# 4 : Pour afficher un ordinateur               #");
		System.out.println("# 5 : Pour ajouter un ordinateur                #");
		System.out.println("# 6 : Pour supprimer un ordinateur              #");
		System.out.println("# 7 : Pour mettre à jour un ordinateur          #");
		System.out.println("# Autre chose pour fermer le programme          #");
		System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # #");
	}
	
}
