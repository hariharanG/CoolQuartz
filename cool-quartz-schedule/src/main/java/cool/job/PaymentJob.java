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

public class PaymentJob  extends QuartzJobBean{
	private Logger logger = LoggerFactory.getLogger(PaymentJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecCtx) throws JobExecutionException {
		logger.info("Started PaymentJob with JobContext {}", jobExecCtx);
		try {
			Thread.sleep(5000L); // to interrupt 5 seconds
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		logger.info("Finished Execution of PaymentJob");		
	}
	
}
