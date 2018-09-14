package com.al.three.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 生产者
 * ClassName: Producer <br/>  
 * date: 2018年9月14日 下午1:04:21 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
@Slf4j
public abstract class Producer<T> implements Runnable{

	@Setter
	private PipeLine<T> pipeLine;
	@Getter
	private String id; //生产者ID
	
	public Producer(String id){
		this.id= id;
	}

    @Override
    public void run() {
        try {
            for(;;){
                T t = produce();
                if(t!=null){
                    pipeLine.put(t);
                    log.info("Producer[{}] produced,PipeLine size: {}",id,pipeLine.size());
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Producer["+id+"] got an err",e);
        }finally {
            pipeLine.unregistProducer(this);
        }
    }

    abstract protected T produce() throws Exception;
	
}
