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

import es.urjc.code.domain.orders.*;
import es.urjc.code.services.*;
import es.urjc.code.web.dtos.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public List<OrderDto> getAll() {
        return orderService.getAll().stream()
                .map(order -> mapper(order))
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        try {
            Order order = this.orderService.get(orderId);
            return new ResponseEntity<>(mapper(order), HttpStatus.OK);
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> newOrder(@RequestBody OrderDto orderDto) {
        try {
            Order order = this.orderService.createOrder(orderDto.getCustomerId(), orderDto.getProductId(), orderDto.getQuanty(), orderDto.getOrderTotal());
            orderDto.setOrderId(order.getId());
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
        } catch (CustomerNotFoundException | ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CustomerCreditLimitExceededException | ProductStockLimitExceededException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    
    private OrderDto mapper(Order order) {
        return new OrderDto(order.getId(),  order.getCustomerId(), order.getProductId(), order.getQuanty(), order.getOrderTotal().getAmount());
    }
}