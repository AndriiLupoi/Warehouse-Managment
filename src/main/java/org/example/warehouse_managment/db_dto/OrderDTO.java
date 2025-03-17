package org.example.warehouse_managment.db_dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.warehouse_managment.model.enums.OrderStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDTO {

    @NotNull(message = "Customer name cannot be empty")
    private String customerName;

    @NotNull(message = "Status cannot be empty")
    private OrderStatus status;

    private LocalDateTime createdAt;
}
