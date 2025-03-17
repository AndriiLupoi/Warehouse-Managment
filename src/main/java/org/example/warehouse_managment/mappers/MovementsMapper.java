package org.example.warehouse_managment.mappers;


import org.example.warehouse_managment.db_dto.MovementDTO;
import org.example.warehouse_managment.model.Movements;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, WarehouseMapper.class})
public interface MovementsMapper {

    MovementsMapper INSTANCE = Mappers.getMapper(MovementsMapper.class);

    @Mapping(source = "product.name", target = "productId")
    @Mapping(source = "fromWarehouse.name", target = "fromWarehouseId")
    @Mapping(source = "toWarehouse.name", target = "toWarehouseId")
    MovementDTO toMovementsDTO(Movements movements);

    Movements toMovements(MovementDTO movementsDTO);
}
