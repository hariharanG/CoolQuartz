package cool.quartz.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * 
 * @author Hari Ganapathy
 *
 */
@ControllerAdvice
public class ExceptionController {
	private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity handleExceptions(WebRequest request, Exception exception){
		logger.error("Err in controller : ", exception);
		return null;
	}
}
