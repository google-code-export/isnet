package com.intrigueit.myc2i.scheduler;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;



public class SchedulerInit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** will repeat after 600000 milisecond**/
	private final Integer REPEAT_INTERVAL = 5000;
	
	protected static final Logger log = Logger.getLogger( SchedulerInit.class );
	
	public void init(ServletConfig config) {
		
		log.debug("Scheduler init called!");

		try{ 
		// Initiate a Schedule Factory
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // Retrieve a scheduler from schedule factory
        Scheduler scheduler = schedulerFactory.getScheduler();
        // current time
        long ctime = System.currentTimeMillis(); 
        // Initiate JobDetail with job name, job group, and executable job class
        JobDetail jobDetail = new JobDetail("jobDetail-s1", "jobDetailGroup-s1", StoryCollator.class);
        // Initiate SimpleTrigger with its name and group name
        SimpleTrigger simpleTrigger = 
        	new SimpleTrigger("simpleTrigger", "triggerGroup-s1");
        // set its start up time
        simpleTrigger.setStartTime(new Date(ctime));
        // set the interval, how often the job should run (10 seconds here) 
        //simpleTrigger.setRepeatInterval(10000);
     // set the interval, how often the job should run (1000 seconds here)
        simpleTrigger.setRepeatInterval(REPEAT_INTERVAL);
        // set the number of execution of this job, set to 10 times. 
        // It will run 10 time and exhaust.
        simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        // set the ending time of this job. 
        // We set it for 60 seconds from its startup time here
        // Even if we set its repeat count to 10, 
        // this will stop its process after 6 repeats as it gets it endtime by then.
        //simpleTrigger.setEndTime(endDate);
        // set priority of trigger. If not set, the default is 5
        //simpleTrigger.setPriority(10);
        // schedule a job with JobDetail and Trigger
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        

		// start the scheduler
        scheduler.start();
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
	  }

	  public void doGet(HttpServletRequest req, HttpServletResponse res) {
	  }}
