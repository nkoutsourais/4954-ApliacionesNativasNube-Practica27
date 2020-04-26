package es.urjc.code;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.Customer;
import es.urjc.code.domain.customers.CustomerRepository;
import es.urjc.code.domain.products.Product;
import es.urjc.code.domain.products.ProductRepository;

@Controller
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void run(String... args) throws ParseException {
        Customer customer1 = new Customer("Neo", new Money(10));
        Customer customer2 = new Customer("Patricia", new Money(30));
        this.customerRepository.save(customer1);
        this.customerRepository.save(customer2);
        Product product1 = new Product("Movil", 10, new Money(4));
        Product product2 = new Product("Tele", 1, new Money(20));
        Product product3 = new Product("Radio", 50, new Money(2));
        this.productRepository.save(product1);
        this.productRepository.save(product2);
        this.productRepository.save(product3);
    }
}