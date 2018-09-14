package com.test;

import org.junit.Test;

import com.al.three.DataMap;
import com.al.three.DataRow;

public class TestDataMap {

	@Test
	public void testPut() {
		DataRow dataRow1 = new DataRow("123,456,12.9");
		DataRow dataRow2 = new DataRow("124,457,12.9");
		DataRow dataRow3 = new DataRow("125,456,10.9");
		DataRow dataRow4 = new DataRow("124,457,15.9");
		
		DataMap.put(dataRow1);
		DataMap.put(dataRow2);
		DataMap.put(dataRow3);
		DataMap.put(dataRow4);
		for(DataRow row : DataMap.get().values()){
			System.out.println(row.toString());
		}
	}
}
