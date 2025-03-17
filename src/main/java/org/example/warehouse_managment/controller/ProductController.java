package org.example.warehouse_managment.controller;

import jakarta.validation.Valid;
import org.example.warehouse_managment.db_dto.ProductDTO;
import org.example.warehouse_managment.exceptions.ProductNotFoundException;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        return productService.saveProduct(productDTO);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }
}
