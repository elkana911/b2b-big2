package com.big.web.b2b_big2.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.big.web.b2b_big2.webapp.listener.StartupListener;

/*
 * http://krams915.blogspot.com/2011/01/spring-3-task-scheduling-via.html
 http://www.mkyong.com/spring/spring-and-java-thread-example/
 
 */
@Component("asyncWorker")
//@Scope("prototype")
public class ASyncWorker implements Worker {
    private static final Log log = LogFactory.getLog(StartupListener.class);
	 
	@Async
	 public void work() {
	  String threadName = Thread.currentThread().getName();
	  log.debug("   " + threadName + " has began working.");
	        try {
	         log.debug("working...");
	            Thread.sleep(10000); // simulates work
	        }
	        catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        log.debug("   " + threadName + " has completed work.");
	 }
}
