package es.urjc.code.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Response;

@FeignClient(name = "webhooks", url = "${monolith.server}")
public interface WebhookService {

    @RequestMapping(method = RequestMethod.POST, value = "api/webh/products/{productId}/reserve", consumes = "application/json")
    Response reserve(@PathVariable("productId") Long productId, ReserveStock reserveStock);

    @RequestMapping(method = RequestMethod.POST, value = "api/webh/customers/{customerId}/credit", consumes = "application/json")
    Response addcredit(@PathVariable("customerId") Long customerId, CreditDto creditExtra);

    @RequestMapping(method = RequestMethod.POST, value = "api/webh/customers/{customerId}/reserve", consumes = "application/json")
    Response reserve(@PathVariable("customerId") Long customerId, CreditDto reserveCredit);
}