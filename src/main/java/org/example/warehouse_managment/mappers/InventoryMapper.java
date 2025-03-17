package org.example.warehouse_managment.mappers;


import org.example.warehouse_managment.db_dto.InventoryDTO;
import org.example.warehouse_managment.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, WarehouseMapper.class})
public interface InventoryMapper {

    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(source = "product.name", target = "productId")
    @Mapping(source = "warehouse.name", target = "warehouseId")
    InventoryDTO toInventoryDTO(Inventory inventory);

    Inventory toInventory(InventoryDTO inventoryDTO);
}
