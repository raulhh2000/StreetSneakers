package urjc.dad.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.ShoppingCart;
import urjc.dad.models.User;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
	Optional<ShoppingCart> findByUser(User user);
}
