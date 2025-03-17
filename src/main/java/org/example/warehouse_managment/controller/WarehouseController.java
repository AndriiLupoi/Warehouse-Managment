package org.example.warehouse_managment.controller;

import jakarta.validation.Valid;
import org.example.warehouse_managment.db_dto.WarehouseDTO;
import org.example.warehouse_managment.model.Warehouse;
import org.example.warehouse_managment.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
@Validated
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable int id) {
        return warehouseService.getWarehouseById(id);
    }

    @PostMapping
    public Warehouse addWarehouse(@Valid @RequestBody WarehouseDTO warehouseDTO) {
        return warehouseService.saveWarehouse(warehouseDTO);
    }

    @PutMapping
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.updateWarehouse(warehouse);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable int id) {
        warehouseService.deleteWarehouse(id);
    }
}
