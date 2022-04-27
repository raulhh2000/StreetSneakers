package urjc.dad.repositories;

import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.Admin;

@CacheConfig(cacheNames="admins")
public interface AdminRepository  extends JpaRepository<Admin, Long> {
	@Cacheable
	Optional<Admin> findByEmail(String email);
	@CacheEvict(cacheNames="admins", allEntries=true)
	Admin save(Admin admin);
	@CacheEvict(cacheNames="admins", allEntries=true)
	void delete(Admin admin);
}
