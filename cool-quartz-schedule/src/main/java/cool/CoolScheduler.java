package cool;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 * 
 * @author Hari Ganapathy
 * 
 *
 */
public class CoolScheduler {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring/cool-quartz-schedule.xml");
	}

}
