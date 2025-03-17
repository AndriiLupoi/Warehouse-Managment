package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.InventoryDTO;
import org.example.warehouse_managment.exceptions.InventoryNotFoundException;
import org.example.warehouse_managment.mappers.InventoryMapper;
import org.example.warehouse_managment.model.Inventory;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.Warehouse;
import org.example.warehouse_managment.repository.InventoryRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.example.warehouse_managment.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(int id) throws InventoryNotFoundException {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if (inventory.isEmpty()) {
            throw new InventoryNotFoundException("Inventory with ID " + id + " not found.");
        }
        return inventory.get();
    }

    public Inventory saveInventory(InventoryDTO inventoryDTO) throws InventoryNotFoundException {
        Inventory inventory = InventoryMapper.INSTANCE.toInventory(inventoryDTO);

        Optional<Product> product = productRepository.findById(inventoryDTO.getProductId());
        if (product.isEmpty()) {
            throw new InventoryNotFoundException("Product not found");
        }
        inventory.setProduct(product.get());

        Optional<Warehouse> warehouse = warehouseRepository.findById(inventoryDTO.getWarehouseId());
        if (warehouse.isEmpty()) {
            throw new InventoryNotFoundException("Warehouse not found");
        }
        inventory.setWarehouse(warehouse.get());

        return inventoryRepository.save(inventory);
    }


    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(int id) {
        inventoryRepository.deleteById(id);
    }
}
