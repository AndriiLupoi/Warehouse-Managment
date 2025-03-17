package org.example.warehouse_managment.db_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @NotNull(message = "Name cannot be empty")
    private String name;

    private int categoryId;

    private int supplierId;

    @Positive(message = "Price must be positive")
    private BigDecimal price;
}
