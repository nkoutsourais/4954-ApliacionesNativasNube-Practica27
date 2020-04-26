package es.urjc.code.services;

import org.springframework.stereotype.Service;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.Customer;

@Service
public class MailService {

    public void send(Customer customer, Money newAmount) {
        System.out.println("El cliente " + customer.getName() + " ha recibido un ingreso de " + newAmount);
    }
}