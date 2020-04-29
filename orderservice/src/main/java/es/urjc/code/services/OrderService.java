package es.urjc.code.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.orders.Order;
import es.urjc.code.domain.orders.OrderNotFoundException;
import es.urjc.code.domain.orders.OrderRepository;
import es.urjc.code.saga.CreateOrderSaga;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CreateOrderSaga createOrderSaga;

    public Order createOrder(Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
        return createOrderSaga.execute(customerId, productId, quanty, orderTotal);
    }

    public Order get(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}