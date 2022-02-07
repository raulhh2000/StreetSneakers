package urjc.dad.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.Purchase;
import urjc.dad.models.User;

public interface PurchaseRepository  extends JpaRepository<Purchase, Long> {
	List<Purchase> findByUser(User user);
}
