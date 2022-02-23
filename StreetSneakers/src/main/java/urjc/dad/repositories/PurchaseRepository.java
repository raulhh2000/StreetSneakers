package urjc.dad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.Purchase;

public interface PurchaseRepository  extends JpaRepository<Purchase, Long> {

}
