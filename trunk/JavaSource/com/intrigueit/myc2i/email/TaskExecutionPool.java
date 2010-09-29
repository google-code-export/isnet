package com.intrigueit.myc2i.email;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("taskPool")
@Scope("session")
public class TaskExecutionPool {
	
	int poolSize = 20;
	 
    int maxPoolSize = 200;
 
    long keepAliveTime = 100;
 
    ThreadPoolExecutor threadPool = null;
    
    final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(15);
    
	protected static final Logger logger = Logger.getLogger( TaskExecutionPool.class );
	
    public TaskExecutionPool(){
        threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);
    }
    public void addTaskToPool(Runnable task)
    {
    	/*
    	logger.debug("Task count in pool .."+threadPool.getTaskCount() );
        threadPool.execute(task);
        logger.debug("Task addded");
    	logger.debug("Task in pool: .."+threadPool.getTaskCount() );
        logger.debug("Queue size: .." + queue.size());
        */
    	Thread thd = new Thread(task);
    	thd.start();
 
    }

    public void shutDown()
    {
        threadPool.shutdown();
    }
    
}
