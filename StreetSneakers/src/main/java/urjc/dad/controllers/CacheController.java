package urjc.dad.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@RestController
public class CacheController {

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.password}")
	private String redisPassword;

	@GetMapping("/cache")
	public Set<String> getKeys() {
		Jedis jedis = new Jedis(redisHost, redisPort);
		jedis.auth(redisPassword);
		Set<String> keysResult = jedis.keys("*");
		jedis.close();
		return keysResult;
	}

	@GetMapping("/cache/{key}")
	public Set<String> getKeys(@PathVariable String key) {
		Jedis jedis = new Jedis(redisHost, redisPort);
		jedis.auth(redisPassword);
		Set<String> keysResult = jedis.keys(key + ":*");
		jedis.close();
		return keysResult;
	}

	@GetMapping("/cache/key/{key}")
	public String getCacheContent(@PathVariable String key) {
		Jedis jedis = new Jedis(redisHost, redisPort);
		jedis.auth(redisPassword);
		String keysResult = jedis.get(key);
		jedis.close();
		return keysResult;
	}
}
