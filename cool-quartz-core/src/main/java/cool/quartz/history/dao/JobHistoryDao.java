package cool.quartz.history.dao;

import java.util.List;

import cool.quartz.history.model.JobHistoryEntity;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public interface JobHistoryDao {
	List<JobHistoryEntity> findAll();
}
