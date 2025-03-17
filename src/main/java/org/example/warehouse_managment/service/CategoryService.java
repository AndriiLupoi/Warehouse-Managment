package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.CategoryDTO;
import org.example.warehouse_managment.exceptions.CategoryNotFoundException;
import org.example.warehouse_managment.mappers.CategoryMapper;
import org.example.warehouse_managment.model.Category;

import org.example.warehouse_managment.repository.CategoryRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(System.out::println);
        return categories;
    }

    public Category getCategoryById(int id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category with ID " + id + " not found.");
        }
        return category.get();
    }


    public Category saveCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException {
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);

        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()) {
            throw new CategoryNotFoundException("Category already exists");
        }

        return categoryRepository.save(category);
    }


    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}