package com.al.three;

import java.util.concurrent.ConcurrentHashMap;
/**
 * 排序处理
 * ClassName: DataLinkedList <br/>  
 * date: 2018年9月14日 下午3:19:56 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
public class DataMap {
	//存放排序后数据
	private static final ConcurrentHashMap<String,DataRow> dataMap = new ConcurrentHashMap<>();
	
	/**
	 * 存放数据
	 */
	public static void put(final DataRow dataRow){
		while(true){
			DataRow oldDataRw = dataMap.get(dataRow.getC2()) ;
			if(oldDataRw == null ){
				if(dataMap.putIfAbsent(dataRow.getC2(), dataRow) != null){ //数据已经存在
					continue ;
				}
				break ;
			}
			if(oldDataRw.compareTo(dataRow) == 0){
				boolean reset = dataMap.replace(dataRow.getC2(),oldDataRw, dataRow);
				if(!reset) continue ;
			}
			break ;
		}
	}
	
	public static ConcurrentHashMap<String, DataRow> get(){
		return dataMap;
	}
	
}
