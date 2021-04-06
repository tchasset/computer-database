package com.excilys.tchasset.client;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.service.CompanyService;
import com.excilys.tchasset.service.ComputerService;

@Component
public class Client {

	private final CompanyService companyService;
	private final ComputerService computerService;
	private final Page page;

	@Autowired
	public Client(CompanyService companyService, ComputerService computerService, Page page) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.page = page;
	}

	public void affiche() {
		Scanner sc = new Scanner(System.in);
		int option = 1;
		while(option>0 && option<8) {
			menu();
			option = sc.nextInt();
			EnumCli enumCli = EnumCli.value(option);

			switch(enumCli) {
				case LISTALLCOMPANY:
					System.out.println(companyService.getCompanies());
					break;
				case LISTCOMPANY:
					menu1Company(sc);
					break;
				case LISTALLCOMPUTER:
					paginateComputer(sc);
					break;
				case LISTCOMPUTER:
					menu1Computer(sc);
					break;
				case ADDCOMPUTER:
					menuAdd(sc);
					break;
				case DELETECOMPUTER:
					menuDelete(sc);
					break;
				case UPDATECOMPUTER:
					menuUpdate(sc);
					break;
				case DELETECOMPANY:
					menuDeleteCompany(sc);
					break;
				default:
					System.out.println("Fin du programme");
					System.exit(0);
			}
			System.out.println("Voulez vous continuez ? (0:non, 1:oui)");
			option = sc.nextInt();
		}
		sc.close();
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
		System.out.println("# 8 : Pour supprimer une companie               #");
		System.out.println("# 0 : Pour fermer le programme                  #");
		System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # #");
	}

	private void menu1Company(Scanner sc) {
		System.out.print("Saisir l'ID de la companie : ");
		int id = sc.nextInt();
		Optional<Company> c = companyService.getById(id);
		if (c.isPresent())
			System.out.println(c.get());
		else
			System.out.println("Aucune companie avec cet ID");
	}

	private Optional<Computer> getComputer(int id) {
		return computerService.getById(id);
	}

	private void menu1Computer(Scanner sc) {
		System.out.print("Saisir l'ID de l'ordinateur à afficher : ");
		int id = sc.nextInt();
		Optional<Computer> c = getComputer(id);
		if (c.isPresent())
			System.out.println(c.get());
		else
			System.out.println("Aucun ordinateur avec cet ID");
	}

	private void menuAdd(Scanner sc) {
		int annee, mois, jour;
		System.out.print("Entrez le nom de l'ordinateur : ");
		String name = sc.useDelimiter("\n").next();
		sc.useDelimiter(" ").nextLine();

		System.out.print("Entrez la date de debut de service au format (jour mois annee) : ");
		jour = sc.nextInt();		mois = sc.nextInt();		annee = sc.nextInt();
		LocalDate introduced = LocalDate.of(annee, mois, jour);

		System.out.print("Entrez la date de fin de service au format (jour mois annee) : ");
		jour = sc.nextInt();		mois = sc.nextInt();		annee = sc.nextInt();
		LocalDate discontinued = LocalDate.of(annee, mois, jour);

		System.out.print("Entrez l'ID de la companie : ");
		sc.nextInt();

		Computer c = new Computer.Builder(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.build();
		computerService.addComputer(c);
	}

	private void menuDelete(Scanner sc) {
		System.out.print("Saisir l'ID de l'ordinateur à supprimer : ");
		int id = sc.nextInt();
		Optional<Computer> c = getComputer(id);
		if (c.isPresent())
			computerService.deleteComputer(c.get().getId());
		else
			System.out.println("Aucun ordinateur avec cet ID");
	}

	private void menuDeleteCompany(Scanner sc) {
		System.out.print("Saisir l'ID de la company à supprimer : ");
		int id = sc.nextInt();
		Optional<Company> c = companyService.getById(id);
		if (c.isPresent())
			companyService.deleteCompany(c.get().getId());
		else
			System.out.println("Aucune company avec cet ID");
	}

	private void menuUpdate(Scanner sc) {
		System.out.print("Saisir l'ID de l'ordinateur à mettre à jour : ");
		int id = sc.nextInt();
		Optional<Computer> c = getComputer(id);
		if (c.isPresent())
			computerService.updateComputer(toModify(c.get(),sc));
		else
			System.out.println("Aucun ordinateur avec cet ID");
	}

	private Computer toModify(Computer computer, Scanner sc) {
		System.out.println("Ancien nom : "+computer.getName());
		System.out.print("Nouveau nom (entrée pour ne pas modifier) : ");
		String name = sc.useDelimiter("\n").next();
		sc.nextLine();
		if(!name.isEmpty())
			computer.setName(name);

		sc.useDelimiter(" ");
		System.out.println("Ancienne date de debut de service : "+computer.getIntroduced());
		System.out.print("Nouvelle date de debut de service au format (jour mois annee) (entrée pour ne pas modifier) : ");
		String jour = sc.next(), 	mois=sc.next(), 	annee=sc.next();
		sc.nextLine();
		if(!jour.isEmpty() && !mois.isEmpty() && (!annee.isEmpty()&&Integer.parseInt(annee)>=1970) )
			computer.setIntroduced(LocalDate.of(Integer.parseInt(annee), Integer.parseInt(mois), Integer.parseInt(jour)));

		System.out.println("Ancienne date de fin de service : "+computer.getDiscontinued());
		System.out.print("Nouvelle date de fin de service au format (jour mois annee) (entrée pour ne pas modifier) : ");
		jour = sc.next(); 	mois=sc.next(); 	annee=sc.next();
		if(!jour.isEmpty() && !mois.isEmpty() && (!annee.isEmpty()&&Integer.parseInt(annee)>=1970))
			computer.setDiscontinued(LocalDate.of(Integer.parseInt(annee), Integer.parseInt(mois), Integer.parseInt(jour)));

		return computer;
	}

	private void paginateComputer(Scanner sc) {
		int continu=1, max=computerService.getNbComputers();
		page.setNbPages(max);

		while(continu==1 || continu==2) {
			System.out.println(computerService.getAllComputers(page));
			if(page.getCurrentPage()==1)
				System.out.print("Page suivante (entrez 1), arreter (entrez 0)");
			else if(page.getCurrentPage()<page.getNbPages())
				System.out.print("Page suivante (entrez 1), page précédente (entrez 2), arreter (entrez 0) ");
			else
				System.out.print("Page précédente (entrez 2), arreter (entrez 0) ");

			continu = sc.nextInt();

			if(continu==1 && page.getCurrentPage()<page.getNbPages()) {
				page.nextPage();
			}
			if(continu==2 && page.getCurrentPage()>0) {
				page.previousPage();
			}
		}
	}
}
