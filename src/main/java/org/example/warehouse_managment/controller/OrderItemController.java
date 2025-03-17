package org.example.warehouse_managment.controller;

import jakarta.validation.Valid;
import org.example.warehouse_managment.db_dto.OrderItemDTO;
import org.example.warehouse_managment.model.OrderItem;
import org.example.warehouse_managment.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-item")
@Validated
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItem> getOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItem(@PathVariable int id) {
        return orderItemService.getOrderItemById(id);
    }

    @PostMapping
    public OrderItem addOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
        return orderItemService.saveOrderItem(orderItemDTO);
    }

    @PutMapping
    public OrderItem updateOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItem(orderItem);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable int id) {
        orderItemService.deleteOrderItem(id);
    }
}
