package urjc.dad.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.User;

public interface UserRepository  extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}