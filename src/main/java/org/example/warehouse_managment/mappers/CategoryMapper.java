package org.example.warehouse_managment.mappers;


import org.example.warehouse_managment.db_dto.CategoryDTO;
import org.example.warehouse_managment.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDTO categoryDTO);

    @Mapping(target = "name", source = "name")
    CategoryDTO toCategoryDTO(Category category);
}
