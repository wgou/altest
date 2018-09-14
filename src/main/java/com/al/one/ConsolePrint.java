package com.al.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述:<br/>TODO; <br/>  
 * ClassName: ConsolePrint <br/>  
 * date: 2018年9月14日 上午11:06:56 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
@Slf4j
public class ConsolePrint {
	
	public static void print(){
		try {
			while(true){
				log.info("input N quit.");
				BufferedReader bd = new BufferedReader(new InputStreamReader(System.in));
				String line = bd.readLine();
				if(line.equals("N")){
					break ;
				}
				log.info(line);
			}
		} catch (IOException e) {
			 log.error(e.getMessage());
		}
	}
}
