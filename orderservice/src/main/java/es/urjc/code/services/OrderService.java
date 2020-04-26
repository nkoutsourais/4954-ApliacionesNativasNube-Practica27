package es.urjc.code.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.orders.Order;
import es.urjc.code.domain.orders.OrderNotFoundException;
import es.urjc.code.domain.orders.OrderRepository;
import es.urjc.code.proxies.CreditDto;
import es.urjc.code.proxies.CustomerService;
import es.urjc.code.proxies.ProductService;
import es.urjc.code.proxies.ReserveStock;
import feign.Response;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public Long createOrder(Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
        Response response = customerService.reserve(customerId, new CreditDto(orderTotal));
        if(response.status() == HttpStatus.OK.value()) {
            response = productService.reserve(productId, new ReserveStock(quanty));
            if(response.status() == HttpStatus.OK.value()) {
                //Ha ido todo bien
                Order order = new Order(customerId, productId, quanty, new Money(orderTotal));
                this.orderRepository.save(order);
                return order.getId();
            } else if(response.status() == HttpStatus.NOT_FOUND.value()) {
                throw new ProductNotFoundException();
            } else if(response.status() == HttpStatus.FORBIDDEN.value()) {
                response = customerService.addcredit(customerId, new CreditDto(orderTotal));
                throw new ProductStockLimitExceededException();
            }
        } else if(response.status() == HttpStatus.NOT_FOUND.value()) {
            throw new CustomerNotFoundException();
        } else if(response.status() == HttpStatus.FORBIDDEN.value()) {
            throw new CustomerCreditLimitExceededException();
        }
        throw new RuntimeException("Error no controlado");
    }

    public Order get(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}