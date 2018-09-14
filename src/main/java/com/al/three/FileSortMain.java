package com.al.three;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import lombok.extern.slf4j.Slf4j;

import com.al.three.base.PipeLine;
@Slf4j
public class FileSortMain {

	/**
	 * 看示例 是根据第二列分组,根据第三列排序.
	 * 故使用一个多个生产消费者 公用一个队列即可
	 */
	public static void execute(){
		  try {
	            PipeLine<String> pipeLine = new PipeLine<String>(100);
	        	String dir = Thread.currentThread().getContextClassLoader().getResource("three").getPath();
	    		File fd = new File(dir);
	    		for(File f : fd.listFiles()){
	    			pipeLine.registerProducer(new FileProducer("pro-"+f.getName(), new BufferedReader(new FileReader(f))));
	    			pipeLine.registerConsumer(new FileConsumer("cons-"+f.getName()));
	    		}
	            pipeLine.start();
	            pipeLine.await();
	            log.info("sort result ------------------------- ");
	            for(DataRow row : DataMap.get().values()){
	            	log.info(row.toString());
	            }
	        } catch (Exception e) {
	            log.error( e.getMessage());
	        }
	}
}
