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
public class RerunJob extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(RerunJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecCtx) throws JobExecutionException {
		logger.info("Restarted BatchReportJob with JobContext {}" , jobExecCtx);
		try {
			Thread.sleep(2000L); // to interrupt 2 seconds
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		logger.info("Finished Execution of BatchReportJob");
	}
	
}
