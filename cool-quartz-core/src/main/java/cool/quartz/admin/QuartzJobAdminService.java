package cool.quartz.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public class QuartzJobAdminService implements JobAdminService, ApplicationContextAware, InitializingBean {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzJobAdminService.class);
	
	private Map<String, Scheduler> schedulerRegistrar = new ConcurrentHashMap<String, Scheduler>();
	
	private AbstractApplicationContext applicationContext;
	
	private static final String ADHOC_GRP = "ADHOC";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.schedulerRegistrar = applicationContext.getBeansOfType(Scheduler.class);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (AbstractApplicationContext) applicationContext;
		
	}

	@Override
	public List<JobKey> listAllJobs() throws Exception {
		List allJobs = new ArrayList();
		for(Map.Entry<String, Scheduler> schedulerEntry: schedulerRegistrar.entrySet()){
			allJobs.addAll(listAllJobs(schedulerEntry.getKey()));
		}
		return allJobs;
	}

	@Override
	public List<JobKey> listAllJobs(String schedulerName) throws Exception {
		List allJobs = new ArrayList();
		Scheduler scheduler = lookupScheduler(schedulerName);
		allJobs.addAll(scheduler.getJobKeys(GroupMatcher.<JobKey>anyGroup()));
		return allJobs;
	}

	private Scheduler lookupScheduler(String schedulerName) {
		try{
			return schedulerRegistrar.get(schedulerName);
		}catch (Exception e) {
			throw new RuntimeException("Scheduler '"+ schedulerName+ "' is not found in registrar", e);
		}		
	}

	@Override
	public Map<String, List<Trigger>> listAllTriggers() throws Exception {
		Map<String, List<Trigger>> allTrigger = new HashMap<String, List<Trigger>>();
		for(Map.Entry<String, Scheduler> schedulerEntry: schedulerRegistrar.entrySet()){
			allTrigger.put(schedulerEntry.getKey(), new ArrayList<Trigger>(listAllTriggers(schedulerEntry.getKey())));
		}
		return allTrigger;
	}

	@Override
	public List<Trigger> listAllTriggers(String schedulerName) throws Exception {
		List allTriggers = new ArrayList();
		Scheduler scheduler = lookupScheduler(schedulerName);
		for(TriggerKey triggerKey: scheduler.getTriggerKeys(GroupMatcher.<TriggerKey>anyGroup())){
			allTriggers.add(scheduler.getTrigger(triggerKey));
		}
		return allTriggers;
	}

	@Override
	public boolean forceExecute(String schedulerName, String jobName, String jobGroupName) {
		Scheduler scheduler = lookupScheduler(schedulerName);
		try{
			Trigger trigger = TriggerBuilder.newTrigger()
					.forJob(jobName, jobGroupName)
					.withIdentity(jobName+ "-"+new Date(), ADHOC_GRP)
					.startNow()
					.build();
			scheduler.scheduleJob(trigger);
			logger.info("JobName: {}, GroupName: {} is forced executed !",jobName,jobGroupName);
			return true;
					
		}catch(SchedulerException e){
			e.printStackTrace();
			return false;
		}				
	}

}
