package cool.quartz.admin;

import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public interface JobAdminService {
	
	List<JobKey> listAllJobs() throws Exception;
	
	List<JobKey> listAllJobs(String schedulerName) throws Exception;
	
	Map<String, List<Trigger>> listAllTriggers() throws Exception;
	
	List<Trigger> listAllTriggers(String schedulerName) throws Exception;
	
	boolean forceExecute(String schedulerName, String jobName, String jobGroupName);

}
