package org.example.warehouse_managment.controller;

import jakarta.validation.Valid;
import org.example.warehouse_managment.db_dto.CategoryDTO;
import org.example.warehouse_managment.exceptions.CategoryNotFoundException;
import org.example.warehouse_managment.model.Category;
import org.example.warehouse_managment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id) throws CategoryNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category addCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        return categoryService.saveCategory(categoryDTO);
    }

    @PutMapping
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }
}
