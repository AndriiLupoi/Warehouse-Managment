package org.example.warehouse_managment.mappers;


import org.example.warehouse_managment.db_dto.ProductDTO;
import org.example.warehouse_managment.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, SupplierMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.name", target = "categoryId")
    @Mapping(source = "supplier.name", target = "supplierId")
    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);
}
