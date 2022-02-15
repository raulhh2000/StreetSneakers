package urjc.dad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.dad.models.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}
