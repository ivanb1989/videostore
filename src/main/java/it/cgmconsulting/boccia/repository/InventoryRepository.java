package it.cgmconsulting.boccia.repository;

import it.cgmconsulting.boccia.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  InventoryRepository extends JpaRepository<Inventory,Long> {
}
