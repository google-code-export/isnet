package com.intrigueit.myc2i.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.scheduling.quartz.QuartzJobBean;

public class WeeklyStoryTrigger extends QuartzJobBean {
	
	protected static final Logger log = Logger.getLogger( WeeklyStoryTrigger.class );
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		try{
			
			log.debug(ctx.getNextFireTime() + "--"+ ctx.getJobRunTime() + "--"+ ctx.getFireTime());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
