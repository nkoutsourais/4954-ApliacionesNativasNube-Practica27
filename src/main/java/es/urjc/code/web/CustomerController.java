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

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.Customer;
import es.urjc.code.domain.customers.CustomerNotFoundException;
import es.urjc.code.services.CustomerService;
import es.urjc.code.web.dtos.CustomerDto;
import es.urjc.code.web.dtos.IncreaseCreditDto;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
	public List<CustomerDto> getAll() {
		return customerService.getAll().stream()
                    .map(customer -> mapper(customer))
                    .collect(Collectors.toList());
    }
    
    @GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) {
        try
        {
            Customer customer = this.customerService.get(customerId);
            return new ResponseEntity<>(mapper(customer), HttpStatus.OK);
        } catch(CustomerNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
	public ResponseEntity<CustomerDto> newCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getName(), new Money(customerDto.getCredit()));
        this.customerService.add(customer);
        customerDto.setId(customer.getId());
		return new ResponseEntity<>(customerDto, HttpStatus.CREATED);
    }

    @PostMapping("/{customerId}/credit")
	public ResponseEntity<CustomerDto> increaseCredir(@PathVariable Long customerId, @RequestBody IncreaseCreditDto increaseDto) {
        this.customerService.increaseCredit(customerId, new Money(increaseDto.getIncreaseCredit()));
		return getCustomer(customerId);
    }
    
    private CustomerDto mapper(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getCreditLimit().getAmount());
    }
}