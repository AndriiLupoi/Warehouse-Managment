package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.ProductDTO;
import org.example.warehouse_managment.exceptions.ProductNotFoundException;
import org.example.warehouse_managment.mappers.ProductMapper;
import org.example.warehouse_managment.model.Category;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.Supplier;
import org.example.warehouse_managment.repository.CategoryRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.example.warehouse_managment.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return product.get();
    }

    public Product saveProduct(ProductDTO productDTO) throws ProductNotFoundException {
        Product product = ProductMapper.INSTANCE.toProduct(productDTO);

        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
        if (category.isEmpty()) {
            throw new ProductNotFoundException("Category not found");
        }
        product.setCategory(category.get());

        Optional<Supplier> supplier = supplierRepository.findById(productDTO.getSupplierId());
        if (supplier.isEmpty()) {
            throw new ProductNotFoundException("Supplier not found");
        }
        product.setSupplier(supplier.get());

        return productRepository.save(product);
    }


    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
