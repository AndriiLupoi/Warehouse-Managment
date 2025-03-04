package org.example.warehouse_managment.service;

import org.example.warehouse_managment.model.Movements;
import org.example.warehouse_managment.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    @Autowired
    MovementRepository movementRepository;

    public List<Movements> getAllMovements() {
        return movementRepository.findAll();
    }

    public Optional<Movements> getMovementById(int id) {
        return movementRepository.findById(id);
    }

    public Movements saveMovement(Movements movement) {
        return movementRepository.save(movement);
    }

    public Movements updateMovement(Movements movement) {
        return movementRepository.save(movement);
    }

    public void deleteMovement(int id) {
        movementRepository.deleteById(id);
    }
}
