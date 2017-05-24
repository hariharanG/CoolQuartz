package cool.persistence;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.listeners.JobListenerSupport;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public class DbPersistJobHistory extends JobListenerSupport implements SchedulerPlugin{
	private Logger logger = LoggerFactory.getLogger(DbPersistJobHistory.class);
	
	private static final String applicationContextSchedulerContextKey = "applicationContext";
	private String jdbcTemplateBeanName = "jdbcTemplate";
	
	// job history query
	private static final String INS_JOB_HIST="insert into qrtz_job_history ( "+
	"HOSTNAME, IP_ADDR, SCHED_NAME, INSTANCE_NAME, JOB_NAME, JOB_GROUP, TRIGGER_NAME, TRIGGER_GROUP,"+
	"FIRED_TIME, NEXT_FIRE_TIME, PRIORITY, IS_NONCONCURRENT, REQUESTS_RECOVERY, RESULT, EXCEPTIONS )"+
	" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ; ";
			
	
	//Job listener support 	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	// Scheduler plugin
	@Override
	public void initialize(String str, Scheduler scheduler, ClassLoadHelper cLoadHelper) throws SchedulerException {
		scheduler.getListenerManager().addJobListener(this, EverythingMatcher.allJobs());
		logger.info("{} is initialized ", getName());		
	}

	//Scheduler plugin
	@Override
	public void shutdown() {
		logger.info("{} is shutdown ", getName());		
	}

	// Scheduler plugin
	@Override
	public void start() {
		logger.info("{} is started", getName());		
	}
	
	// Special overridden 
	@Override
	public void jobWasExecuted(JobExecutionContext jobExecContext, JobExecutionException jobException){
		JdbcTemplate jdbcTemplate=getJdbcTemplate(jobExecContext);
		try{
			jdbcTemplate.update(INS_JOB_HIST, new Object[]{
				getCurrentHostName(), 
				getCurrentIpAddress(),
				jobExecContext.getScheduler().getSchedulerName(),
				jobExecContext.getScheduler().getSchedulerInstanceId(),
				jobExecContext.getJobDetail().getKey().getName(),
				jobExecContext.getJobDetail().getKey().getGroup(),
				jobExecContext.getTrigger().getKey().getName(),
				jobExecContext.getTrigger().getKey().getGroup(),
				jobExecContext.getFireTime(),
				jobExecContext.getTrigger().getNextFireTime(),
				jobExecContext.getTrigger().getPriority(),
				true,
				jobExecContext.isRecovering(),
				getJobExecutionResult(jobExecContext, jobException),
				getJobExecutionResultMessage(jobExecContext, jobException)
				
			});
		}catch(Exception e){
			logger.info("Job history persistence is failed ", e);
			throw new RuntimeException("Job history persistence failure ", e);
		}
	}

	private Object getJobExecutionResultMessage(JobExecutionContext jobExecContext,
			JobExecutionException jobException) {
		return null == jobException ? "": ExceptionUtils.getStackTrace(jobException);
	}

	private Object getJobExecutionResult(JobExecutionContext jobExecContext, JobExecutionException jobException) {
		return null == jobException ? "SUCCESS" : "FAIL";
	}

	private String getCurrentIpAddress() {
		try{
			return InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException uhe){
			return "IP_UNKNOWN";
		}		
	}

	private String getCurrentHostName() {				
		try{
			return InetAddress.getLocalHost().getHostName();
		}catch (UnknownHostException uhe) {
			return "HOST_UNKNOWN";
		}
	}

	private JdbcTemplate getJdbcTemplate(JobExecutionContext jobExecContext) {
		try {
			return ((ApplicationContext) jobExecContext.getScheduler().getContext().get(applicationContextSchedulerContextKey)).getBean(this.jdbcTemplateBeanName,JdbcTemplate.class);		
		} catch (SchedulerException | BeansException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error: JdbcTemplate bean with id = "+this.jdbcTemplateBeanName , e); 
		}		
	}
	
}
