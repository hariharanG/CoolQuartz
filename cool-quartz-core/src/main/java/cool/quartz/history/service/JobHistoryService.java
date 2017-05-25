package cool.quartz.history.service;

import java.util.List;

import cool.quartz.history.model.JobHistoryEntity;

/**
 * 
 * @author Hari Ganapathy
 *
 */

public interface JobHistoryService {
	List<JobHistoryEntity> listAllHistory();
}
