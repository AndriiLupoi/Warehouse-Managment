package org.example.warehouse_managment.mappers;


import org.example.warehouse_managment.db_dto.WarehouseDTO;
import org.example.warehouse_managment.model.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    WarehouseDTO toWarehouseDTO(Warehouse warehouse);

    Warehouse toWarehouse(WarehouseDTO warehouseDTO);
}