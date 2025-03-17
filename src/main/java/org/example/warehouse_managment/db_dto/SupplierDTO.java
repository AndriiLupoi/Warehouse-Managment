package org.example.warehouse_managment.db_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SupplierDTO {

    @NotNull(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String contactInfo;
}
