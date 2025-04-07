package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.warehouse_managment.db_dto.InventoryDTO;
import org.example.warehouse_managment.model.Inventory;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.Warehouse;
import org.example.warehouse_managment.repository.InventoryRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.example.warehouse_managment.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test") //H2 база в тестах
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private Warehouse warehouse;
    private Inventory inventory;

    @BeforeEach
    void setup() {
        inventoryRepository.deleteAll();
        productRepository.deleteAll();
        warehouseRepository.deleteAll();

        product = productRepository.save(new Product("Product 1",null, null, BigDecimal.valueOf(23)));
        warehouse = warehouseRepository.save(new Warehouse("Warehouse 1"));
    }

    @Test
    void testGetAllInventories() throws Exception {
        inventory = inventoryRepository.save(new Inventory(product, warehouse, 100));

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testAddInventorySuccess() throws Exception {
        InventoryDTO inventoryDTO = new InventoryDTO(product.getId(), warehouse.getId(), 150);

        mockMvc.perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryDTO)))
                .andExpect(status().isOk()) // Очікуємо статус 200
                .andExpect(jsonPath("$.product.id", is(product.getId())))
                .andExpect(jsonPath("$.warehouse.id", is(warehouse.getId())))
                .andExpect(jsonPath("$.quantity", is(150)));
    }

    @Test
    void testGetInventoryById() throws Exception {
        inventory = inventoryRepository.save(new Inventory(product, warehouse, 100));

        mockMvc.perform(get("/inventory/" + inventory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(100)))
                .andExpect(jsonPath("$.product.id", is(product.getId())))
                .andExpect(jsonPath("$.warehouse.id", is(warehouse.getId())));
    }

    @Test
    void testUpdateInventory() throws Exception {
        inventory = inventoryRepository.save(new Inventory(product, warehouse, 100));

        InventoryDTO inventoryDTO = new InventoryDTO(product.getId(), warehouse.getId(), 150);

        mockMvc.perform(put("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(150)));
    }

    @Test
    void testDeleteInventory() throws Exception {
        Inventory inventory = inventoryRepository.save(new Inventory(1, product, warehouse, 100));
        // Видалення інвентарю
        mockMvc.perform(delete("/inventory/" + inventory.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/category/" + inventory.getId()))
                .andExpect(status().isNotFound());

    }

}
