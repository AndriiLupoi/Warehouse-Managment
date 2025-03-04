package org.example.warehouse_managment.repository;

import org.example.warehouse_managment.model.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movements, Integer> {
}
