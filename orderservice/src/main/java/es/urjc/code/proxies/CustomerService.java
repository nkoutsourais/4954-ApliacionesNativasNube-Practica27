package es.urjc.code.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Response;

@FeignClient(name = "customers", url = "http://monolito:8080/")
public interface CustomerService {

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/{customerId}/credit", consumes = "application/json")
    Response addcredit(@PathVariable("customerId") Long customerId, CreditDto creditExtra);

    @RequestMapping(method = RequestMethod.POST, value = "api/customers/{customerId}/reserve", consumes = "application/json")
    Response reserve(@PathVariable("customerId") Long customerId, CreditDto reserveCredit);
}