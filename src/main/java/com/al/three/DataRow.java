package com.al.three;

import java.util.List;

import lombok.Getter;

import com.google.common.base.Splitter;

@Getter
public class DataRow implements Comparable<DataRow>{
	
	public DataRow(String line){
		List<String> lineList = Splitter.on(",").splitToList(line);
		this.c1 = lineList.get(0);
		this.c2 = lineList.get(1);
		this.c3 = Float.valueOf(lineList.get(2));
	}
	private String c1;
	private String c2;
	private float c3;
	@Override
	public int compareTo(DataRow o) {
		return this.getC3() > o.getC3() ? 0 :1 ;
	}
	@Override
	public String toString() {
		return new StringBuffer().append(c1).append(",")
				.append(c2).append(",")
				.append(c3).toString();
	}
}
