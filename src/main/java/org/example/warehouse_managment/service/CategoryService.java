package org.example.warehouse_managment.service;

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
        categories.forEach(System.out::println); // Вивід у консоль
        return categories;
    }




    public Optional<Category> getCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            System.out.println("Category found: " + category.get());
        } else {
            System.out.println("No category found with id: " + id);
        }
        return category;
    }


    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}