package com.al;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.al.one.ConsolePrint;
import com.al.three.FileSortMain;
import com.al.two.Single;


/**
 * 测试
 * ClassName: Test <br/>  
 * date: 2018年9月14日 上午11:44:13 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
public class TestExample {
	
	@Test
	public void testOne(){
		ConsolePrint.print();
	}
	
	@Test
	public void TestTwo() throws Exception{
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		Future<Integer> f1 = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Single.newInstance().setX(10);
				return Single.newInstance().getX();
			}
		});
		TimeUnit.SECONDS.sleep(1);
		Future<Integer> f2 = executorService.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return Single.newInstance().getX();
			}
		});
		try{
			assertEquals(new Integer(10),f1.get());
			assertEquals(new Integer(10),f2.get()); //f1先执行完成后,F2获取的应该是F1改变后的值.因使用同一对象
		}catch(Exception ex){
			throw ex;
		}
	}
	
	@Test
	public void testThree(){
		FileSortMain.execute();
	}
}
