package com.intrigueit.myc2i.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class StoryCollator implements Job {
	
	private final String PERIOD_WEEKLY= "weekly";
	
	protected static final Logger log = Logger.getLogger( StoryCollator.class );
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		log.debug("In SimpleQuartzJob - executing its JOB at " + new Date() + " by " + context.getTrigger().getName());

		
	}

}
