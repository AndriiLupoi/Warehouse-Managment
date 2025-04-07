package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.warehouse_managment.db_dto.WarehouseDTO;
import org.example.warehouse_managment.model.Warehouse;
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
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("test") // H2 база в тестах
public class WarehouseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Warehouse warehouse;

    @BeforeEach
    void setup() {
        warehouseRepository.deleteAll();

        warehouse = new Warehouse(1, "Warehouse 1", "Location 1");
        warehouseRepository.save(warehouse);
    }

    @Test
    void testGetAllWarehouses() throws Exception {
        mockMvc.perform(get("/warehouse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testGetWarehouseById() throws Exception {
        mockMvc.perform(get("/warehouse/" + warehouse.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(warehouse.getId())))
                .andExpect(jsonPath("$.name", is(warehouse.getName())))
                .andExpect(jsonPath("$.location", is(warehouse.getLocation())));
    }

    @Test
    void testAddWarehouseSuccess() throws Exception {
        WarehouseDTO warehouseDTO = new WarehouseDTO("Warehouse 2", "Location 2");

        mockMvc.perform(post("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Warehouse 2")))
                .andExpect(jsonPath("$.location", is("Location 2")));
    }

    @Test
    void testUpdateWarehouse() throws Exception {
        Warehouse updatedWarehouse = new Warehouse(warehouse.getId(), "Updated Warehouse", "Updated Location");

        mockMvc.perform(put("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedWarehouse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Warehouse")))
                .andExpect(jsonPath("$.location", is("Updated Location")));
    }

    @Test
    void testDeleteWarehouse() throws Exception {
        mockMvc.perform(delete("/warehouse/" + warehouse.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/warehouse/" + warehouse.getId()))
                .andExpect(status().isNotFound());
    }
}
