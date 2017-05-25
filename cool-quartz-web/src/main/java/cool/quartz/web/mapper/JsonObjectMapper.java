package cool.quartz.web.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Hari Ganapathy
 *
 */
public class JsonObjectMapper extends ObjectMapper {
	
	public JsonObjectMapper(){
		super();
		this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY );		
	}

}
