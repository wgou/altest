package com.al.three;

import java.io.BufferedReader;

import com.al.three.base.Producer;

/**
 * 生产者读取文件
 * ClassName: FileProducer <br/>  
 * date: 2018年9月14日 下午1:56:16 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
public class FileProducer extends Producer<String>{
	private BufferedReader reader ;
	public FileProducer(String id,BufferedReader reader ){
		super(id);
		this.reader = reader;
	}
	@Override
	protected String produce() throws Exception {
		//BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
		String line = reader.readLine();
		return line;
	}

}
