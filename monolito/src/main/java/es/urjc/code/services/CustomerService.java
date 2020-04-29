package es.urjc.code.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.Customer;
import es.urjc.code.domain.customers.CustomerNotFoundException;
import es.urjc.code.domain.customers.CustomerRepository;
import es.urjc.code.services.notifications.CustomerNotification;
import es.urjc.code.services.notifications.CustomerNotificationProxyService;
import es.urjc.code.services.notifications.CustomerNotificationService;
import es.urjc.code.services.notifications.Message;

@Service
public class CustomerService {

    @Value("${feature.toggle.notification.proxy}")
    private boolean proxy = false;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    CustomerNotificationProxyService customerNotificationProxyService;
    @Autowired
    CustomerNotificationService customerNotificationService;

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
        getCustomerNotification()
                .send(new Message("El cliente " + customer.getName() + " ha recibido un ingreso de " + extra.toString()));
    }

	public void reserveCredit(Long customerId, Money reserve) {
        Customer customer = get(customerId);
        customer.reserveCredit(reserve);
        this.customerRepository.save(customer);
    }
    
    private CustomerNotification getCustomerNotification() {
        if(proxy)
            return customerNotificationProxyService;
        return customerNotificationService;
    }
}