package urjc.dad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableCaching
@SpringBootApplication
public class StreetSneakersApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreetSneakersApplication.class, args);
	}
	
}
