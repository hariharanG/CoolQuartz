package cool.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public class BatchReportJob extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(BatchReportJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecCtx) throws JobExecutionException {
		logger.info("Started BatchReportJob with JobContext {} ",  jobExecCtx);
		try {
			Thread.sleep(10000L); // interrupt 10 seconds 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		logger.info("Finished Execution of BatchReportJob ");
	}
	
}
