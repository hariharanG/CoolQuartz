package cool.quartz.history.service;

import java.util.List;

import cool.quartz.history.dao.JobHistoryDao;
import cool.quartz.history.model.JobHistoryEntity;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public class QuartzJobHistoryService implements JobHistoryService {
	
	private JobHistoryDao jobHistoryDao;
		
	public QuartzJobHistoryService(JobHistoryDao jobHistoryDao) {		
		this.jobHistoryDao = jobHistoryDao;
	}

	@Override
	public List<JobHistoryEntity> listAllHistory() {
		return jobHistoryDao.findAll();
	}
	
}
