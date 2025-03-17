package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.MovementDTO;
import org.example.warehouse_managment.exceptions.MovementsNotFoundException;
import org.example.warehouse_managment.mappers.MovementsMapper;
import org.example.warehouse_managment.model.Movements;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.Warehouse;
import org.example.warehouse_managment.repository.MovementRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.example.warehouse_managment.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    @Autowired
    MovementRepository movementRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public List<Movements> getAllMovements() {
        return movementRepository.findAll();
    }

    public Movements getMovementById(int id) throws MovementsNotFoundException {
        Optional<Movements> movements = movementRepository.findById(id);
        if (movements.isEmpty()) {
            throw new MovementsNotFoundException("Movement with ID " + id + " not found.");
        }
        return movements.get();
    }

    public Movements saveMovement(MovementDTO movementDTO) {
        Movements movement = MovementsMapper.INSTANCE.toMovements(movementDTO);

        Optional<Product> product = productRepository.findById(movementDTO.getProductId());
        if (product.isEmpty()) {
            throw new MovementsNotFoundException("Product not found");
        }
        movement.setProduct(product.get());

        Optional<Warehouse> fromWarehouse = warehouseRepository.findById(movementDTO.getFromWarehouseId());
        if (fromWarehouse.isEmpty()) {
            throw new MovementsNotFoundException("From warehouse not found");
        }
        movement.setFromWarehouse(fromWarehouse.get());

        Optional<Warehouse> toWarehouse = warehouseRepository.findById(movementDTO.getToWarehouseId());
        if (toWarehouse.isEmpty()) {
            throw new MovementsNotFoundException("To warehouse not found");
        }
        movement.setToWarehouse(toWarehouse.get());

        return movementRepository.save(movement);
    }


    public Movements updateMovement(Movements movement) {
        return movementRepository.save(movement);
    }

    public void deleteMovement(int id) {
        movementRepository.deleteById(id);
    }
}
