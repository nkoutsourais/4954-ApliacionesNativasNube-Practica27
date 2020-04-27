package es.urjc.code.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.domain.customers.CustomerCreditLimitExceededException;
import es.urjc.code.domain.customers.CustomerNotFoundException;
import es.urjc.code.domain.orders.Order;
import es.urjc.code.domain.orders.OrderNotFoundException;
import es.urjc.code.domain.products.ProductNotFoundException;
import es.urjc.code.domain.products.ProductStockLimitExceededException;
import es.urjc.code.services.OrderService;
import es.urjc.code.web.dtos.CreateOrderRequestDto;
import es.urjc.code.web.dtos.CreateOrderResponseDto;
import es.urjc.code.web.dtos.GetOrderDto;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public List<GetOrderDto> getAll() {
        return orderService.getAll().stream()
                .map(order -> mapper(order))
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderDto> getOrder(@PathVariable Long orderId) {
        try {
            Order order = this.orderService.get(orderId);
            return new ResponseEntity<>(mapper(order), HttpStatus.OK);
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<CreateOrderResponseDto> newOrder(@RequestBody CreateOrderRequestDto order) {
        try {
            Long orderId = this.orderService.createOrder(order.getCustomerId(), order.getProductId(), order.getQuanty(), order.getOrderTotal());
            return new ResponseEntity<>(new CreateOrderResponseDto(orderId), HttpStatus.CREATED);
        } catch (CustomerNotFoundException ex) {
            return new ResponseEntity<>(new CreateOrderResponseDto("Customer not found"), HttpStatus.NOT_FOUND);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(new CreateOrderResponseDto("Product not found"), HttpStatus.NOT_FOUND);
        } catch (CustomerCreditLimitExceededException ex) {
            return new ResponseEntity<>(new CreateOrderResponseDto("Customer haven't credit"), HttpStatus.FORBIDDEN);
        } catch (ProductStockLimitExceededException ex) {
            return new ResponseEntity<>(new CreateOrderResponseDto("Product haven't stock"), HttpStatus.FORBIDDEN);
        }
    }
    
    private GetOrderDto mapper(Order order) {
        return new GetOrderDto(order.getId(),  order.getCustomerId(), order.getProductId(), order.getQuanty(), order.getOrderTotal().getAmount());
    }
}