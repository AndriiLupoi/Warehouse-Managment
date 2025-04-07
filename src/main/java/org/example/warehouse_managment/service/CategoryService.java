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
import org.slf4j.Logger;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);


    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(System.out::println);
        return categories;
    }

    public Category getCategoryById(int id) throws CategoryNotFoundException {
        logger.info("Fetching category with ID: {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            logger.error("Category with ID {} not found.", id);
            throw new CategoryNotFoundException("Category with ID " + id + " not found.");
        }
        logger.info("Category found: {}", category.get());
        return category.get();
    }


    public Category saveCategory(CategoryDTO categoryDTO) throws CategoryNotFoundException {
        logger.info("Saving new category with name: {}", categoryDTO.getName());
        Category category = CategoryMapper.INSTANCE.toCategory(categoryDTO);

        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()) {
            logger.error("Category with name {} already exists.", categoryDTO.getName());
            throw new CategoryNotFoundException("Category already exists");
        }

        Category savedCategory = categoryRepository.save(category);
        logger.info("Category saved with ID: {}", savedCategory.getId());
        return savedCategory;
    }


    public Category updateCategory(Category category) {
        logger.info("Updating category with ID: {}", category.getId());
        Category updatedCategory = categoryRepository.save(category);
        logger.info("Category updated with ID: {}", updatedCategory.getId());
        return updatedCategory;
    }

    public void deleteCategory(int id) {
        logger.info("Deleting category with ID: {}", id);
        categoryRepository.deleteById(id);
        logger.info("Category with ID {} deleted successfully.", id);
    }
}