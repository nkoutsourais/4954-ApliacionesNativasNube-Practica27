package es.urjc.code.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.CustomerCreditLimitExceededException;
import es.urjc.code.domain.customers.CustomerNotFoundException;
import es.urjc.code.domain.products.ProductNotFoundException;
import es.urjc.code.domain.products.ProductStockLimitExceededException;
import es.urjc.code.services.CustomerService;
import es.urjc.code.services.ProductService;
import es.urjc.code.web.dtos.CustomerCreditDto;
import es.urjc.code.web.dtos.ReserveStock;

@RestController
@RequestMapping("/api/webh")
public class WebhookController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;

    @PostMapping("customers/{customerId}/credit")
	public void increaseCredit(@PathVariable Long customerId, @RequestBody CustomerCreditDto creditDto) {
        this.customerService.increaseCredit(customerId, new Money(creditDto.getCredit()));
    }

    @PostMapping("customers/{customerId}/reserve")
	public ResponseEntity<?> reserveCredit(@PathVariable Long customerId, @RequestBody CustomerCreditDto creditDto) {
        try
        {
            this.customerService.reserveCredit(customerId, new Money(creditDto.getCredit()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(CustomerNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(CustomerCreditLimitExceededException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("products/{productId}/reserve")
	public ResponseEntity<?> reserveStock(@PathVariable Long productId, @RequestBody ReserveStock reserveStock) {
        try
        {
            this.productService.reserveStock(productId, reserveStock.getQuanty());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(ProductStockLimitExceededException ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}