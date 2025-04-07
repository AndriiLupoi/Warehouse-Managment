package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.warehouse_managment.db_dto.ProductDTO;
import org.example.warehouse_managment.model.Category;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.Supplier;
import org.example.warehouse_managment.repository.CategoryRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.example.warehouse_managment.repository.SupplierRepository;
import org.example.warehouse_managment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test") // H2 база в тестах
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();

        Category category = categoryRepository.save(new Category(1, "Electro"));
        Supplier supplier = supplierRepository.save(new Supplier(1,"Illya"));

        product = new Product(1, "Product 1", category, supplier, BigDecimal.valueOf(23.0));
        productRepository.save(product);
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testGetProductById() throws Exception {
        mockMvc.perform(get("/product/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product.getId())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.price", is(product.getPrice().doubleValue())));
    }

    @Test
    void testAddProductSuccess() throws Exception {
        ProductDTO productDTO = new ProductDTO("Product 2", 1, 1, BigDecimal.valueOf(150));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product 2")))
                .andExpect(jsonPath("$.price", is(150)));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product(product.getId(), "Updated Product", null, null, BigDecimal.valueOf(100));

        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(100)));
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Видаляти продукт
        mockMvc.perform(delete("/product/" + product.getId()))
                .andExpect(status().isOk());

        // Перевірка, чи продукт більше не існує
        mockMvc.perform(get("/product/" + product.getId()))
                .andExpect(status().isNotFound());  // Повертає 404 після видалення
    }
}
