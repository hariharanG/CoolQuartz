package cool.quartz.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobKey;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cool.quartz.admin.JobAdminService;
import cool.quartz.history.model.JobHistoryEntity;
import cool.quartz.history.service.JobHistoryService;

/**
 * 
 * @author Hari Ganapathy
 *
 */
@Controller
public class JobMonitorController {
	private Logger logger = LoggerFactory.getLogger(JobMonitorController.class);
	
	@Resource
	private JobHistoryService jobHistoryService;
	
	@Resource
	private JobAdminService jobAdminService;
	
	@RequestMapping(value="/~cool-quartz/history", method=RequestMethod.GET)
	@ResponseBody
	public PageImpl<JobHistoryEntity> listAllHitsory(@PageableDefaults final Pageable pageable){
		if(null == pageable){
			return new PageImpl<JobHistoryEntity>(jobHistoryService.listAllHistory());
		}else{
			logger.debug("Received Pageable: {}", pageable);
			return new PageImpl<JobHistoryEntity>(jobHistoryService.listAllHistory());
		}
	}
	
	@RequestMapping(value="/~cool-quartz/jobs", method=RequestMethod.GET)
	@ResponseBody
	public List<JobKey> listAllJobs() throws Exception{
		return jobAdminService.listAllJobs();
	}
	
	@RequestMapping(value="/~cool-quartz/triggers", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Trigger>> listAllTriggers() throws Exception{
		return jobAdminService.listAllTriggers();
	}
	
	@RequestMapping(value="/~cool-force-quartz/{schedulerName}/{jobGroup}/{jobName}",method=RequestMethod.POST)
	public void forceExecutable(@PathVariable("schedulerName") final String schedulerName,
								@PathVariable("jobGroup") final String jobGroup,
								@PathVariable("jobName") final String jobName ) throws Exception{
		jobAdminService.forceExecute(schedulerName, jobGroup, jobName);
	}
	
	@RequestMapping("/")
	public ModelAndView index(){
		return new ModelAndView("index.jsp");
	}
	
	
}
