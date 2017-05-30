package cool;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/** 
 * 
 * @author Hari Ganapathy
 * 
 *
 */
@SpringBootApplication
public class CoolScheduler {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring/cool-quartz-schedule.xml");
		SpringApplication.run(CoolScheduler.class, args);
	}

}
