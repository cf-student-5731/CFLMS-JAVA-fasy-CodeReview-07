package businessLogic;

import java.util.ArrayList;

/**
* custom container for a database container
with columns titles as String[]
and content as ArrayList of different Objects
*/

public class MyTable {
	private String[] titles;
	private ArrayList<ArrayList<Object>> dataList = new ArrayList<>();


//	return ArrayList as Object[][] to handle different types for display table
	public Object[][] getDataListAsObject() {
		Object[][] data = new Object[dataList.size()][dataList.get(0).size()];
		for (int i = 0; i < dataList.size(); i++) {
			for (int j = 0; j < dataList.get(0).size(); j++) {
				data[i][j] = dataList.get(i).get(j);
			}
		}
		return data;
	}

	public void setDataList(ArrayList<ArrayList<Object>> dataList) {
		this.dataList = dataList;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public String[] getTitles() {
		return titles;
	}
}