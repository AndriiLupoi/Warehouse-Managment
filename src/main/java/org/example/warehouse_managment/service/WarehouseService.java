package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.WarehouseDTO;
import org.example.warehouse_managment.exceptions.WarehouseNotFoundException;
import org.example.warehouse_managment.mappers.WarehouseMapper;
import org.example.warehouse_managment.model.Warehouse;
import org.example.warehouse_managment.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(int id) throws WarehouseNotFoundException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        if (warehouse.isEmpty()) {
            throw new WarehouseNotFoundException("Warehouse with ID " + id + " not found.");
        }
        return warehouse.get();
    }

    public Warehouse saveWarehouse(WarehouseDTO warehouseDTO) throws WarehouseNotFoundException {
        Warehouse warehouse = WarehouseMapper.INSTANCE.toWarehouse(warehouseDTO);

        return warehouseRepository.save(warehouse);
    }


    public Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(int id) {
        warehouseRepository.deleteById(id);
    }
}
