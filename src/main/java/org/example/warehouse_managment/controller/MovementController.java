package org.example.warehouse_managment.controller;

import org.example.warehouse_managment.model.Movements;
import org.example.warehouse_managment.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movement")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping
    public List<Movements> getMovements() {
        return movementService.getAllMovements();
    }

    @GetMapping("/{id}")
    public Movements getMovement(@PathVariable int id) {
        return movementService.getMovementById(id).orElse(null);
    }

    @PostMapping
    public Movements addMovement(@RequestBody Movements movement) {
        return movementService.saveMovement(movement);
    }

    @PutMapping
    public Movements updateMovement(@RequestBody Movements movement) {
        return movementService.updateMovement(movement);
    }

    @DeleteMapping("/{id}")
    public void deleteMovement(@PathVariable int id) {
        movementService.deleteMovement(id);
    }
}
