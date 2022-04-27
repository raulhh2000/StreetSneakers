package urjc.dad.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

	@Autowired
	private CacheManager cacheManager;

	@GetMapping("/cache")
	public List<Map<Object, Object>> getCacheContent() {
		
		List<Map<Object, Object>> listCaches= new ArrayList<>();
		for(String cacheName: cacheManager.getCacheNames()) {
			listCaches.add((Map<Object, Object>) cacheManager.getCache(cacheName).getNativeCache());
		}
		return listCaches;
	}
}
