package es.urjc.code.saga;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.orders.Order;
import es.urjc.code.domain.orders.OrderRepository;
import es.urjc.code.proxies.CreditDto;
import es.urjc.code.proxies.ReserveStock;
import es.urjc.code.proxies.WebhookService;
import feign.Response;

@Component
public class CreateOrderSaga {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebhookService webhookService;

    public Order execute(Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
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
}