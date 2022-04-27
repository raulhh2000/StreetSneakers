package urjc.dad;


import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@EnableCaching
@SpringBootApplication
public class StreetSneakersApplication {

	private static final Log LOG = LogFactory.getLog(StreetSneakersApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(StreetSneakersApplication.class, args);
	}
	
	@Bean
	public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
		LOG.info("Activating cache...");
		return new HazelcastCacheManager(hazelcastInstance);
	}
	
	@Bean
	public Config config() {
		Config config = new Config();
		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);
		joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList("127.0.0.1"));
		return config;
	}

}
