package geo.db;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	private final AutowireCapableBeanFactory beanFactory;
	
	
	@Autowired
	public CacheConfig(AutowireCapableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	
	@Bean
	public CoordinatesDataService dataService() {
		return beanFactory.createBean(CoordinatesDataService.class);
	}
	
	
	@Bean
	@Override
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("data")));
		return cacheManager;
	}
	
}
