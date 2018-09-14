package com.al.two;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClassName: Single <br/>  
 * date: 2018年9月14日 上午11:40:34 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class Single {
	
	//private static final Single SINGLE = new Single();
	private volatile static Single SINGLE = null;
	@Setter
	@Getter
	private Integer x = 0; //设置全局变量 ,用于测试多线程线程安全
	
	public static Single newInstance(){
		if(SINGLE !=null){
			return SINGLE;
		}
		synchronized (Single.class) {
			SINGLE = new Single();
			return SINGLE;
		}
	}
	
}
