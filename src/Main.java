import dbConnector.DbConnector;

import java.sql.Connection;
import java.sql.SQLClientInfoException;

public class Main {

	public static void main(String[] args) {

//		initialize UserInterface with DbConnector class
		UserInterface ui = new UserInterface(new DbConnector());
	}
}
