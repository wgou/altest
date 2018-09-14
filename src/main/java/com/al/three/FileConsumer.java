package com.al.three;

import com.al.three.base.Consumer;

public class FileConsumer extends Consumer<String>{

	public FileConsumer(String id) {
		super(id);  
	}

	@Override
	public void consumer(String t) throws Exception {
		DataRow dataRow = new DataRow(t);
		DataMap.put(dataRow);
	}
}
