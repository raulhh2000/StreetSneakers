package urjc.dad.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import urjc.dad.models.Product;

public interface ProductRepository  extends JpaRepository<Product, Long> {

	Optional<Product> findByName(String name);
	List<Product> findBySize(double size);
	List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(double priceMin, double priceMax);
	List<Product> findByPriceGreaterThanEqual(double priceMin);
	List<Product> findByPriceLessThanEqual(double priceMax);
	List<Product> findByBrand(String brand);
	@Query("SELECT DISTINCT d.brand FROM Product d")
	public List<String> findDistinctBrand();
}
