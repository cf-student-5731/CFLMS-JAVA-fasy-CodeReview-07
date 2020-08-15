package businessLogic;

import dbConnector.DbConnector;

import java.sql.*;
import java.util.ArrayList;

public class SQLMethods {
	private DbConnector connector;

	public SQLMethods(DbConnector connection) {
		this.connector = connection;
	}


//	------hardcoded table display output
//	public void displayAllDataFromStudents() {
//		try {
//			Statement statement = connector.getConnection().createStatement();
//			ResultSet resultSet = statement.executeQuery("select * from students");
//			while (resultSet.next()) {
//				int id = resultSet.getInt("id");
//				String name = resultSet.getString("firstname");
//				String address = resultSet.getString("lastname");
//				String eMail = resultSet.getString("email");
//				System.out.printf("%-4d%-50s%-50s%-50s%n", id, name, address, eMail);
//			}
//		} catch (SQLException e) {
//			System.out.printf("%n%s", "ERROR occurred while executing SQL!");
//			e.printStackTrace();
//		}
//	}


	public MyTable getAllDataFromSpecificTable(String table) {
		MyTable myTable = new MyTable();
		ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
		try {
			String sql = "SELECT * FROM " + table;
//			create statement
			Statement statement = connector.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

//			get metadata
			ResultSetMetaData metadataSet = resultSet.getMetaData();

			int columnCount = metadataSet.getColumnCount();
			String[] titles = new String[columnCount];

//			resolve resultSet
			while (resultSet.next()) {
				ArrayList<Object> objectList = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					titles[i - 1] = metadataSet.getColumnName(i).toUpperCase();
					if (metadataSet.getColumnType(i) == 4) {
						objectList.add(resultSet.getInt(metadataSet.getColumnName(i)));
					} else {
						objectList.add(resultSet.getString(metadataSet.getColumnName(i)));
					}
					if (i % columnCount == 0) {
						dataList.add(objectList);
					}
				}

//				add titles to myTable Object
				myTable.setTitles(titles);
			}
//				add data to myTable Object
			myTable.setDataList(dataList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myTable;
	}


	public MyTable getSpecificElementFromSpecificTableByElementId(String table, int id) throws IdOutOfRangeException {
		MyTable myTable = new MyTable();
		ArrayList<ArrayList<Object>> dataList = new ArrayList<>();

		try {
			int rowCount = 0;
//			check if id is out of range
			Statement statement = connector.getConnection().createStatement();
			ResultSet rowCounter = statement.executeQuery("select * from " + table);
			while (rowCounter.next()) {
				rowCount++;
			}
			if (id < 0 || id > rowCount) {
				throw new IdOutOfRangeException("Id not found in table, try again!");
			} else {

//			create statement
				String sql = "SELECT * FROM " + table +
						" WHERE id=?;";
				PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql);
				preparedStatement.setInt(1, id);

//			execute query
				ResultSet resultSet = preparedStatement.executeQuery();
//			get metadata
				ResultSetMetaData metadataSet = resultSet.getMetaData();

				int columnCount = metadataSet.getColumnCount();
				String[] titles = new String[columnCount];

//			resolve resultSet
				while (resultSet.next()) {
					ArrayList<Object> objectList = new ArrayList<>();
					for (int i = 1; i <= columnCount; i++) {
						titles[i - 1] = metadataSet.getColumnName(i).toUpperCase();
						if (metadataSet.getColumnType(i) == 4) {
							objectList.add(resultSet.getInt(metadataSet.getColumnName(i)));
						} else {
							objectList.add(resultSet.getString(metadataSet.getColumnName(i)));
						}
						if (i % columnCount == 0) {
							dataList.add(objectList);
						}
					}

//				add titles to myTable Object
					myTable.setTitles(titles);
				}
//				add data to myTable Object
				myTable.setDataList(dataList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return myTable;
	}


	//	get data of specific teacher
	public MyTable getClassesFromSpecificTeacher(int id) {
		MyTable myTable = new MyTable();
		ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
		try {
//			create statement
			String sql = "SELECT classes.* " +
					"FROM teachers " +
					"INNER JOIN teaches " +
					"ON teachers.id = teaches.fk_teacher " +
					"INNER JOIN classes " +
					"ON classes.id = teaches.fk_class " +
					"WHERE teachers.id= ? ;";

			PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, id);

//			execute query
			ResultSet resultSet = preparedStatement.executeQuery();
//			get metadata
			ResultSetMetaData metadataSet = resultSet.getMetaData();

			int columnCount = metadataSet.getColumnCount();
			String[] titles = new String[columnCount];

//			resolve resultSet
			while (resultSet.next()) {
				ArrayList<Object> objectList = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					titles[i - 1] = metadataSet.getColumnName(i).toUpperCase();
					if (metadataSet.getColumnType(i) == 4) {
						objectList.add(resultSet.getInt(metadataSet.getColumnName(i)));
					} else {
						objectList.add(resultSet.getString(metadataSet.getColumnName(i)));
					}
					if (i % columnCount == 0) {
						dataList.add(objectList);
					}
				}
//				add titles to myTable Object
				myTable.setTitles(titles);
			}
//				add data to myTable Object
			myTable.setDataList(dataList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myTable;
	}

}
