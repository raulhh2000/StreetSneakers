package urjc.dad.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.Product;

public interface ProductRepository  extends JpaRepository<Product, Long> {

	Optional<Product> findByName(String name);
	List<Product> findBySize(double size);
}
