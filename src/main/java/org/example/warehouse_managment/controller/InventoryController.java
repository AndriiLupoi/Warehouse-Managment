package org.example.warehouse_managment.controller;

import jakarta.validation.Valid;
import org.example.warehouse_managment.db_dto.InventoryDTO;
import org.example.warehouse_managment.exceptions.InventoryNotFoundException;
import org.example.warehouse_managment.model.Inventory;
import org.example.warehouse_managment.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@Validated
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable int id) {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping
    public Inventory addInventory(@Valid @RequestBody InventoryDTO inventoryDTO) throws InventoryNotFoundException {
        return inventoryService.saveInventory(inventoryDTO);
    }

    @PutMapping
    public Inventory updateInventory(@RequestBody Inventory inventory) {
        return inventoryService.updateInventory(inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable int id) {
        inventoryService.deleteInventory(id);
    }
}
