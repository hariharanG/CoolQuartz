package cool.quartz.history.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import cool.quartz.history.model.JobHistoryEntity;

public class MybatisJobHistoryDao extends SqlSessionDaoSupport implements JobHistoryDao {

	@Override
	public List<JobHistoryEntity> findAll() {
		return getSqlSession().selectList(getMapper() + ".selectAll");
	}

	private String getMapper() {
		return "cool.quartz.history.dao.JobHistoryMapper";
	}

}
