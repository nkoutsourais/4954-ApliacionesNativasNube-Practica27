package es.urjc.code.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.Customer;
import es.urjc.code.domain.customers.CustomerNotFoundException;
import es.urjc.code.domain.customers.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerNotificationService customerNotificationService;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer get(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer add(String name, Money creditLimit) {
        Customer customer = new Customer(name, creditLimit);
        this.customerRepository.save(customer);
        return customer;
    }

    public void increaseCredit(Long customerId, Money extra) {
        Customer customer = get(customerId);
        customer.increaseCredit(extra);
        this.customerRepository.save(customer);
        this.customerNotificationService.send(customer, extra);
    }

	public void reserveCredit(Long customerId, Money reserve) {
        Customer customer = get(customerId);
        customer.reserveCredit(reserve);
        this.customerRepository.save(customer);
	}
}