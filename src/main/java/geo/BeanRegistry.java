package geo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanRegistry implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	public Object getBean(String name) {
		return this.applicationContext.getBean(name);
	}
	
	public <T> T getBean(Class<T> requiredType) {
		return this.applicationContext.getBean(requiredType);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;		
	}

	public boolean containsBean(String string) {
		return this.applicationContext.containsBean(string);
	}

}
