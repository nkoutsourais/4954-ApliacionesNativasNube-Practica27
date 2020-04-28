package es.urjc.code.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.domain.Money;
import es.urjc.code.services.CustomerService;
import es.urjc.code.web.dtos.CustomerCreditDto;

@RestController
@RequestMapping("/api/webh")
public class WebhookController {

    @Autowired
    CustomerService customerService;

    @PostMapping("customers/{customerId}/credit")
	public void increaseCredit(@PathVariable Long customerId, @RequestBody CustomerCreditDto increaseDto) {
        this.customerService.increaseCredit(customerId, new Money(increaseDto.getCredit()));
    }
}