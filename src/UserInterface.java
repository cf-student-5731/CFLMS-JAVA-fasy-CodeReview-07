import businessLogic.IdOutOfRangeException;
import businessLogic.MyTable;
import businessLogic.SQLMethods;
import dbConnector.DbConnector;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
	//	class variables
	private DbConnector connector;
	private SQLMethods sqlMethods;
	private boolean exit01 = false;


	public UserInterface(DbConnector connector) {
		this.connector = connector;
		this.sqlMethods = new SQLMethods(connector);
//	display menu immediately after initialisation
		buildMenu();
	}

	private void buildMenu() {
		displayMenuHeader();
		handleInputMainMenu();
	}

	//	display menu header
	private void displayMenuHeader() {
		String singleLine = "----------------------------------------";
		String doubleLine = "========================================";
		String pattern01 = "%1s  %-13s %-18s  %5s%n";

		System.out.printf("%n%1s%s%1s%n", "+", doubleLine, "+");
		System.out.printf(pattern01, "|", " ", "Welcome", "|");
		System.out.printf(pattern01, "|", " ", "to the", "|");
		System.out.printf(pattern01, "|", " ", "SCHOOL!", "|");
		System.out.printf("%1s%s%1s%n", "+", singleLine, "+");
	}

	//	display menu body
	private void printMenuBody() {
		String menuPattern = "%-4s%s%n";
		System.out.printf(menuPattern, "1)", "Display all students.");
		System.out.printf(menuPattern, "2)", "Display all teachers.");
		System.out.printf(menuPattern, "3)", "Display all classes.");
		System.out.printf(menuPattern, "4)", "Display one specific teacher by id.");
		System.out.printf(menuPattern, "5)", "Create report of teachers.");
		System.out.printf(menuPattern, "0)", "EXIT.");
		System.out.println();
		System.out.print("Enter your choice: ");
	}


	private void handleInputMainMenu() {
		int input01 = -1;
		Scanner in = new Scanner(System.in);
		while (!exit01) {
			System.out.println();
			printMenuBody();
			try {
				input01 = in.nextInt();

//			translate  any invalid input to error case
			} catch (Exception e) {
				input01 = -1;
			}
			switch (input01) {

//				exit case
				case 0:
					System.out.println("Bye!");
					connector.disConnect();
					exit01 = true;
					break;

//				menu cases
				case 1:
//					display table students
					System.out.println("LIST OF ALL STUDENTS:");
					displayMyTable(sqlMethods.getAllDataFromSpecificTable("students"));
					break;


				case 2:
//					display table teachers
					System.out.println("LIST OF ALL TEACHERS:");
					displayMyTable(sqlMethods.getAllDataFromSpecificTable("teachers"));
					break;


				case 3:
//					display table classes
					System.out.println("LIST OF ALL CLASSES:");
					displayMyTable(sqlMethods.getAllDataFromSpecificTable("classes"));
					break;


				case 4:
					System.out.println("DATA OF THE TEACHER:");
					System.out.print("Input teacher id: ");
					int input02 = -1;
					try {
						input02 = in.nextInt();
						System.out.printf("%n");
						try {
							displayMyTable(sqlMethods.getSpecificElementFromSpecificTableByElementId("teachers", input02));
							System.out.println("TEACHES:");
							displayMyTable(sqlMethods.getClassesFromSpecificTeacher(input02));
							break;
							
						} catch (IdOutOfRangeException e) {
							System.out.println(e.getMessage());
							handleInputMainMenu();
						}
					} catch (InputMismatchException e) {
						// input02 stays 01 so get one level up
					}
					break;
				case 5:
					writeMyTableToFile(sqlMethods.getAllDataFromSpecificTable("teachers"));
					break;


//				catch invalid input
				default:
					System.out.println("Invalid Input, please try again");
//					redundant method call
					handleInputMainMenu();
			}
		}
	}


	private void writeMyTableToFile(MyTable mytable) {
		try {
			PrintWriter pw = new PrintWriter("./data/" + "report.txt");
//		extract titles from myTable object
			String[] titles = mytable.getTitles();
//		extract data from myTable object
			Object[][] data = mytable.getDataListAsObject();

//			write titles
			for (int i = 0; i < titles.length; i++) {
				if (i == 0) {
					pw.printf("%-10s", titles[i]);
				} else {
					pw.printf("%-45s", titles[i]);
				}
			}
			pw.print("\n");

//			write data
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					if (j == 0) {
						pw.printf("%-10d", data[i][j]);
					} else {
						pw.printf("%-45s", data[i][j]);
					}
				}
				pw.print("\n");
			}

			System.out.println("REPORT written to ./data/report.txt");
			pw.close();

		} catch (IOException e) {
			System.out.println("ERROR while writing file!");
		}
	}


	private void displayMyTable(MyTable mytable) {
//		extract titles from myTable object
		String[] titles = mytable.getTitles();
//		extract data from myTable object
		Object[][] data = mytable.getDataListAsObject();

//		display titles
		for (int i = 0; i < titles.length; i++) {
			if (i == 0) {
				System.out.printf("%-10s", titles[i]);
			} else {
				System.out.printf("%-45s", titles[i]);
			}
		}

//		display data
		for (int i = 0; i < data.length; i++) {
			System.out.println();
			for (int j = 0; j < data[i].length; j++) {
				if (j == 0) {
					System.out.printf("%-10d", data[i][j]);
				} else {
					System.out.printf("%-45s", data[i][j]);
				}
			}
		}
		System.out.println();
	}

}
