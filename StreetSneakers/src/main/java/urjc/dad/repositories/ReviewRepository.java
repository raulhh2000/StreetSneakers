package urjc.dad.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.Review;
import urjc.dad.models.User;

public interface ReviewRepository  extends JpaRepository<Review, Long> {

	List<Review> findByUser(User user);
}
