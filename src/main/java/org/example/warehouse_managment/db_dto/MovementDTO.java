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
public class MovementDTO {

    @NotNull(message = "Product cannot be null")
    private int productId;

    @NotNull(message = "From warehouse cannot be null")
    private int fromWarehouseId;

    @NotNull(message = "To warehouse cannot be null")
    private int toWarehouseId;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    private LocalDateTime movedAt;
}