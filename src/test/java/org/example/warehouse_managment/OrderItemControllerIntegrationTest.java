package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.warehouse_managment.db_dto.OrderItemDTO;
import org.example.warehouse_managment.model.Order;
import org.example.warehouse_managment.model.OrderItem;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.model.enums.OrderStatus;
import org.example.warehouse_managment.repository.OrderItemRepository;
import org.example.warehouse_managment.repository.OrderRepository;
import org.example.warehouse_managment.repository.ProductRepository;
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
@ActiveProfiles("test") //H2 база в тестах
public class OrderItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        orderItemRepository.deleteAll();

        // Створення тестових об'єктів
        order = orderRepository.save(new Order(1, "Customer 1", OrderStatus.pending));
        product = productRepository.save(new Product(1, "Product 1", null, null, BigDecimal.valueOf(23)));

        orderItem = orderItemRepository.save(new OrderItem(1, order, product, 10, BigDecimal.valueOf(230.0)));
    }

    @Test
    void testGetAllOrderItems() throws Exception {
        mockMvc.perform(get("/order-item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void testGetOrderItemById() throws Exception {

        mockMvc.perform(get("/order-item/" + orderItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(orderItem.getQuantity())))
                .andExpect(jsonPath("$.price", is(orderItem.getPrice().doubleValue())))
                .andExpect(jsonPath("$.order.id", is(order.getId())))
                .andExpect(jsonPath("$.product.id", is(product.getId())));
    }

    @Test
    void testAddOrderItemSuccess() throws Exception {
        OrderItemDTO orderItemDTO = new OrderItemDTO(5, BigDecimal.valueOf(150), product.getId(), order.getId());

        mockMvc.perform(post("/order-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(5)))
                .andExpect(jsonPath("$.price", is(150)))
                .andExpect(jsonPath("$.order.id", is(order.getId())))
                .andExpect(jsonPath("$.product.id", is(product.getId())));
    }


    @Test
    void testUpdateOrderItem() throws Exception {
        OrderItem updatedOrderItem = new OrderItem(orderItem.getId(), order, product, 20, BigDecimal.valueOf(450));

        mockMvc.perform(put("/order-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrderItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(20)))
                .andExpect(jsonPath("$.price", is(450)));
    }

    @Test
    void testDeleteOrderItem() throws Exception {
        mockMvc.perform(delete("/order-item/" + orderItem.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/order-item/" + orderItem.getId()))
                .andExpect(status().isNotFound());
    }

}
