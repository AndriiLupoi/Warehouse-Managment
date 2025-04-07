package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.warehouse_managment.db_dto.SupplierDTO;
import org.example.warehouse_managment.model.Supplier;
import org.example.warehouse_managment.repository.SupplierRepository;
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
public class SupplierControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Supplier supplier;

    @BeforeEach
    void setup() {
        supplierRepository.deleteAll();

        supplier = new Supplier(1, "Supplier 1", "supplier1@example.com");
        supplierRepository.save(supplier);
    }

    @Test
    void testGetAllSuppliers() throws Exception {
        mockMvc.perform(get("/supplier"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testGetSupplierById() throws Exception {
        mockMvc.perform(get("/supplier/" + supplier.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(supplier.getId())))
                .andExpect(jsonPath("$.name", is(supplier.getName())))
                .andExpect(jsonPath("$.contactInfo", is(supplier.getContactInfo())));
    }

    @Test
    void testAddSupplierSuccess() throws Exception {
        SupplierDTO supplierDTO = new SupplierDTO("Supplier 2", "supplier2@example.com");

        mockMvc.perform(post("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Supplier 2")))
                .andExpect(jsonPath("$.contactInfo", is("supplier2@example.com")));
    }

    @Test
    void testUpdateSupplier() throws Exception {
        Supplier updatedSupplier = new Supplier(supplier.getId(), "Updated Supplier", "updated@example.com");

        mockMvc.perform(put("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSupplier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Supplier")))
                .andExpect(jsonPath("$.contactInfo", is("updated@example.com")));
    }

    @Test
    void testDeleteSupplier() throws Exception {
        mockMvc.perform(delete("/supplier/" + supplier.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/supplier/" + supplier.getId()))
                .andExpect(status().isNotFound());
    }
}
