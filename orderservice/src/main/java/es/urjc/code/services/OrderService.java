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
import es.urjc.code.proxies.ReserveStock;
import es.urjc.code.proxies.WebhookService;
import feign.Response;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebhookService webhookService;

    public Order createOrder(Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
        Response response = webhookService.reserve(customerId, new CreditDto(orderTotal));
        if(response.status() == HttpStatus.OK.value()) {
            response = webhookService.reserve(productId, new ReserveStock(quanty));
            if(response.status() == HttpStatus.OK.value()) {
                //Ha ido todo bien
                Order order = new Order(customerId, productId, quanty, new Money(orderTotal));
                this.orderRepository.save(order);
                return order;
            } else if(response.status() == HttpStatus.NOT_FOUND.value()) {
                throw new ProductNotFoundException();
            } else if(response.status() == HttpStatus.FORBIDDEN.value()) {
                response = webhookService.addcredit(customerId, new CreditDto(orderTotal));
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