package cool.quartz;

import java.io.IOException;

import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cool.quartz.admin.JobAdminService;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public class CoolCoreAdmin {
	
	private static Logger logger = LoggerFactory.getLogger(CoolCoreAdmin.class);
	
	private static OptionParser optionParser;
	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/cool-quartz-core.xml");
		JobAdminService jobAdminService = applicationContext.getBean("jobAdminService", JobAdminService.class);
		
		OptionSet optionSet = parseCmdLineOptions(args);
		
		if(optionSet.has("help")){
			optionParser.printHelpOn(System.out);
			closeApplication(applicationContext);
			return;
		}
		
		if(optionSet.has("execute") && optionSet.has("jobname") && optionSet.has("groupname")){
			String jobname = (String) optionSet.valueOf("jobname");
			String groupname = (String) optionSet.valueOf("groupname");
			
			System.out.println("*****************************************************");
			System.out.println("\tForce Executing a specified JOB");
			System.out.println("*****************************************************");
			boolean result = jobAdminService.forceExecute("reportScheduler", jobname, groupname);
			if(result){
				System.out.println(groupname+"."+jobname+" is force execution SUCCESS");
			}
			System.out.println("**************************************************************************\n");
			closeApplication(applicationContext);
			return;
		}
		
		if(optionSet.has("list") && optionSet.has("jobs")){
			System.out.println("*****************************************************");
			System.out.println("\tList All Jobs");
			System.out.println("*****************************************************");
			for(JobKey jobKey : jobAdminService.listAllJobs()){
				System.out.println(jobKey);
			}
			System.out.println("**************************************************************************\n");
			closeApplication(applicationContext);
			return;
		}

	}

	private static void closeApplication(ClassPathXmlApplicationContext applicationContext) {
		applicationContext.close();
		
	}

	private static OptionSet parseCmdLineOptions(String[] args) throws IOException {
		OptionParser optionParser = new OptionParser();
		
		optionParser.accepts("execute", "force execute option group").withOptionalArg().ofType(String.class);
		optionParser.accepts("jobname", "job name").withRequiredArg().ofType(String.class);
		optionParser.accepts("groupname", "job group name").withOptionalArg().ofType(String.class).defaultsTo("DEFAULT");
		
		optionParser.accepts("jobs","list jobs").withOptionalArg().ofType(String.class);
		optionParser.accepts("list", "list all").requiredIf("jobs").withOptionalArg().ofType(String.class);
		
		optionParser.accepts("help", "print help message").withOptionalArg().ofType(String.class);
		
		CoolCoreAdmin.optionParser = optionParser;
		return optionParser.parse(args);
		
	}

}
