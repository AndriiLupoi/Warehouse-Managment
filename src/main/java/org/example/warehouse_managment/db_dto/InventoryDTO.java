package org.example.warehouse_managment.db_dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryDTO {

    @NotNull(message = "Product cannot be null")
    private int productId;

    @NotNull(message = "Warehouse cannot be null")
    private int warehouseId;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    private LocalDateTime lastUpdated;
}