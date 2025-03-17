package org.example.warehouse_managment.db_dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WarehouseDTO {

    @NotNull(message = "Name cannot be empty")
    private String name;

    private String location;
}
