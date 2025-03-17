package org.example.warehouse_managment.controller;

import jakarta.validation.Valid;
import org.example.warehouse_managment.db_dto.MovementDTO;
import org.example.warehouse_managment.exceptions.MovementsNotFoundException;
import org.example.warehouse_managment.model.Movements;
import org.example.warehouse_managment.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movement")
@Validated
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping
    public List<Movements> getMovements() {
        return movementService.getAllMovements();
    }

    @GetMapping("/{id}")
    public Movements getMovement(@PathVariable int id) {
        return movementService.getMovementById(id);
    }

    @PostMapping
    public Movements addMovement(@Valid @RequestBody MovementDTO movementDTO) throws MovementsNotFoundException {
        return movementService.saveMovement(movementDTO);
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
