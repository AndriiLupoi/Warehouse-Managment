package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.warehouse_managment.db_dto.MovementDTO;
import org.example.warehouse_managment.model.Movements;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.Warehouse;
import org.example.warehouse_managment.repository.MovementRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.example.warehouse_managment.repository.WarehouseRepository;
import org.example.warehouse_managment.service.MovementService;
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
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test") //H2 база в тестах
public class MovementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private Warehouse warehouseFrom;
    private Warehouse warehouseTo;
    private Movements movement;

    @BeforeEach
    void setup() {
        movementRepository.deleteAll();
        productRepository.deleteAll();
        warehouseRepository.deleteAll();

        product = productRepository.save(new Product(1,"Product 1",null, null, BigDecimal.valueOf(23)));
        warehouseFrom = warehouseRepository.save(new Warehouse(1,"Warehouse 1"));
        warehouseTo = warehouseRepository.save(new Warehouse(2,"Warehouse 2"));

        movement = movementRepository.save(new Movements(1, product, warehouseFrom, warehouseTo, 100));
    }

    @Test
    void testGetAllMovements() throws Exception {
        mockMvc.perform(get("/movement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testGetMovementById() throws Exception {
        mockMvc.perform(get("/movement/" + movement.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(100)))
                .andExpect(jsonPath("$.product.id", is(product.getId())))
                .andExpect(jsonPath("$.fromWarehouse.id", is(warehouseFrom.getId())))
                .andExpect(jsonPath("$.toWarehouse.id", is(warehouseTo.getId())));
    }

    @Test
    void testAddMovementSuccess() throws Exception {
        MovementDTO movementDTO = new MovementDTO(product.getId(), warehouseFrom.getId(), warehouseTo.getId(), 150);

        mockMvc.perform(post("/movement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movementDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(150)))
                .andExpect(jsonPath("$.product.id", is(product.getId())))
                .andExpect(jsonPath("$.fromWarehouse.id", is(warehouseFrom.getId())))
                .andExpect(jsonPath("$.toWarehouse.id", is(warehouseTo.getId())));
    }

    @Test
    void testUpdateMovement() throws Exception {
        Movements updatedMovement = new Movements(movement.getId(), product, warehouseFrom, warehouseTo, 200, LocalDateTime.now());

        mockMvc.perform(put("/movement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMovement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(200)));
    }

    @Test
    void testDeleteMovement() throws Exception {
        Movements movement2 = movementRepository.save(new Movements(2, product, warehouseFrom, warehouseTo, 200));

        mockMvc.perform(delete("/movement/" + movement2.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/movement/" + movement2.getId()))
                .andExpect(status().isNotFound());
    }
}
