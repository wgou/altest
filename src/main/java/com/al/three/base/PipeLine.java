package com.al.three.base;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Maps;

/**
 * 注册器,管理生产者与消费者. 同时启动 同时销毁.
 * ClassName: PipeLine <br/>  
 * date: 2018年9月14日 下午1:05:32 <br/>  
 * @author  苟伟(gouwei@dmall.com)   
 * @version
 */
@Slf4j
public class PipeLine<T> {

	private Map<Producer<T>,Thread> producerMap ;
	private Map<Consumer<T>,Thread> consumerMap ;
	private BlockingQueue<T> queue ;
	private int maxSize;
	private ReentrantLock lock ; 
	private CountDownLatch latch ; //管道注册服务管理控制器
	private  volatile boolean started; //启动标记
	
	public PipeLine(int maxSize){
		producerMap = Maps.newConcurrentMap();
		consumerMap = Maps.newConcurrentMap();
		lock = new ReentrantLock();
		queue=new ArrayBlockingQueue<>(maxSize);
        this.maxSize=maxSize;
	}
	 
	public  PipeLine<T> registerProducer(Producer<T> producer){
		lock.lock();
		try{
			producer.setPipeLine(this);
			producerMap.put(producer, new Thread(producer));
			log.info("Register producer[{}],count {}",producer.getId(),producerMap.size());
			return this;
		}finally{
			lock.unlock();
		}
	}
	 
	public  PipeLine<T> registerConsumer(Consumer<T> consumer){
		lock.lock();
		try{
			consumer.setPipeLine(this);
			consumerMap.put(consumer, new Thread(consumer));
			log.info("Register consumer[{}],count {}",consumer.getId(),consumerMap.size());
			return this;
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * 启动
	 */
	public PipeLine<T>  start(){
		lock.lock();
		try{
		  if(started){
                log.warn("PipeLine has been started!");
                return this;
            }
			latch = new CountDownLatch(producerMap.size() + consumerMap.size());
			for(Thread producer : producerMap.values()){
				producer.start();
			}
			for(Thread consumer : consumerMap.values()){
				consumer.start();
			}
			started = true;
			return this;
		}finally{
			lock.unlock();
		}
	}
	/**
	 * 加入队列
	 */
	public void put(T t) throws InterruptedException{
		if(maxSize == queue.size()){
			throw new ArrayIndexOutOfBoundsException("queue full. size:" + maxSize);
		}
		queue.put(t);
	}

    T take() throws InterruptedException {
        return queue.poll(1, TimeUnit.SECONDS);
    }
    
    public int producerCount(){
        lock.lock();
        try {
            if (queue.size() > 0) {
                return 1;
            }
            return producerMap.size();
        } finally {
            lock.unlock();
        }
    }

    public int size(){
        return queue.size();
    }
	

    public void unregistProducer(Producer<T> producer){
        lock.lock();
        try{
            producerMap.remove(producer);
            log.info("Unregist producer[{}], count {}",producer.getId(),producerMap.size());
        }finally {
            latch.countDown();
            lock.unlock();
        }
    }
    public void unregistConsumer(Consumer<T> consumer){
        lock.lock();
        try{
            consumerMap.remove(consumer);
            int size = consumerMap.size();
            if (size==0){ //消费者注销完毕. 认为程序结束. 
                started=false;
            }
            log.info("Unregist consumer[{}], count {}",consumer.getId(),size);
        }finally {
            latch.countDown();
            lock.unlock();
        }
    }
    
    public void await() throws InterruptedException{
    	latch.await();
    }
}
