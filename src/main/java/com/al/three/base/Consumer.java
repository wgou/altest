package com.al.three.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者
 * ClassName: Consumer <br/>  
 * date: 2018年9月14日 下午1:04:51 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
@Slf4j
public abstract class Consumer<T> implements Runnable{
	@Setter
	private PipeLine<T> pipeLine;
	@Getter
	private String id; //consumer ID
	
	public Consumer(String id){
		this.id= id;
	}
	
	@Override
	public void run() {
		  
		try {  
			for(;;){
				T  t = pipeLine.take();
				if(t !=null){
					consumer(t);
				}else{
					if(pipeLine.producerCount()==0){
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("consumer[{}] err." + e.getMessage(),id);
		}finally{
			pipeLine.unregistConsumer(this);
		}
	}
	
	public abstract void consumer(T t) throws Exception;
}
