package org.example.warehouse_managment.service;

import org.example.warehouse_managment.db_dto.OrderDTO;
import org.example.warehouse_managment.exceptions.OrderNotFoundException;
import org.example.warehouse_managment.mappers.OrderMapper;
import org.example.warehouse_managment.model.Order;
import org.example.warehouse_managment.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order with ID " + id + " not found.");
        }
        return order.get();
    }

    public Order saveOrder(OrderDTO orderDTO) throws OrderNotFoundException {
        Order order = OrderMapper.INSTANCE.toOrder(orderDTO);

        return orderRepository.save(order);
    }


    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
