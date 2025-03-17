package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.OrderItemDTO;
import org.example.warehouse_managment.exceptions.OrderItemNotFoundException;
import org.example.warehouse_managment.mappers.OrderItemMapper;
import org.example.warehouse_managment.model.Order;
import org.example.warehouse_managment.model.OrderItem;
import org.example.warehouse_managment.model.Product;
import org.example.warehouse_managment.repository.OrderItemRepository;
import org.example.warehouse_managment.repository.OrderRepository;
import org.example.warehouse_managment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(int id) throws OrderItemNotFoundException {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isEmpty()) {
            throw new OrderItemNotFoundException("Order Item with ID " + id + " not found.");
        }
        return orderItem.get();
    }

    public OrderItem saveOrderItem(OrderItemDTO orderItemDTO) throws OrderItemNotFoundException {
        OrderItem orderItem = OrderItemMapper.INSTANCE.toOrderItem(orderItemDTO);

        Optional<Product> product = productRepository.findById(orderItemDTO.getProductId());
        if (product.isEmpty()) {
            throw new OrderItemNotFoundException("Product not found");
        }
        orderItem.setProduct(product.get());

        Optional<Order> order = orderRepository.findById(orderItemDTO.getOrderId());
        if (order.isEmpty()) {
            throw new OrderItemNotFoundException("Order not found");
        }
        orderItem.setOrder(order.get());

        return orderItemRepository.save(orderItem);
    }


    public OrderItem updateOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(int id) {
        orderItemRepository.deleteById(id);
    }
}
