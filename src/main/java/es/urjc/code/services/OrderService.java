package es.urjc.code.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.customers.Customer;
import es.urjc.code.domain.orders.Order;
import es.urjc.code.domain.orders.OrderNotFoundException;
import es.urjc.code.domain.orders.OrderRepository;
import es.urjc.code.domain.products.Product;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public Long createOrder(Long customerId, Long productId, int quanty) {
        Product product = productService.get(customerId);
        product.reserveStock(quanty);
        Customer customer = customerService.get(productId);
        customer.reserveCredit(product.calculatePrice(quanty));
        //Ha ido todo bien
        Order order = new Order(customer, product, quanty);
        this.orderRepository.save(order);
        return order.getId();
    }

    public Order get(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}