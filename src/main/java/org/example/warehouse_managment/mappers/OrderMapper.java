package org.example.warehouse_managment.mappers;


import org.example.warehouse_managment.db_dto.OrderDTO;
import org.example.warehouse_managment.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toOrderDTO(Order order);

    Order toOrder(OrderDTO orderDTO);
}
