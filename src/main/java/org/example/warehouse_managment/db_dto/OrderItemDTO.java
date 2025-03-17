package org.example.warehouse_managment.db_dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.warehouse_managment.validation.ValidPrice;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    @Positive(message = "Quantity must be positive")
    private int quantity;

    @ValidPrice(message = "Price must be greater than zero")
    private BigDecimal price;

    private int productId;
    private int orderId;
}
