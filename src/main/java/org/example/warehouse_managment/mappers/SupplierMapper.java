package org.example.warehouse_managment.mappers;

import org.example.warehouse_managment.db_dto.SupplierDTO;
import org.example.warehouse_managment.model.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    Supplier toSupplier(SupplierDTO supplierDTO);

    @Mapping(target = "supplierNameAndContact", expression = "java(supplier.getName() + \" - \" + supplier.getContactInfo())")
    SupplierResponse toSupplierResponse(Supplier supplier);
}
