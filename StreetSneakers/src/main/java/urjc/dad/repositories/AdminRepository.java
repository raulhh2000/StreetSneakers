package urjc.dad.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Long> {
	
	Optional<Admin> findByEmail(String email);
}
