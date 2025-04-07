package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.warehouse_managment.db_dto.OrderDTO;
import org.example.warehouse_managment.model.Order;
import org.example.warehouse_managment.model.enums.OrderStatus;
import org.example.warehouse_managment.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@ActiveProfiles("test") //H2 база в тестах
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();

        order = orderRepository.save(new Order(1, "Customer 1", OrderStatus.pending));
    }

    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testGetOrderById() throws Exception {
        mockMvc.perform(get("/order/" + order.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName", is(order.getCustomerName())))
                .andExpect(jsonPath("$.status", is(order.getStatus().toString())));
    }

    @Test
    void testAddOrderSuccess() throws Exception {
        OrderDTO orderDTO = new OrderDTO("Customer 2", OrderStatus.pending, null);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName", is("Customer 2")))
                .andExpect(jsonPath("$.status", is(OrderStatus.pending.toString())));
    }

    @Test
    void testUpdateOrder() throws Exception {
        Order updatedOrder = new Order(order.getId(), "Updated Customer", OrderStatus.delivered);

        mockMvc.perform(put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName", is("Updated Customer")))
                .andExpect(jsonPath("$.status", is(OrderStatus.delivered.toString())));
    }

    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/order/" + order.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/order/" + order.getId()))
                .andExpect(status().isNotFound());
    }
}
