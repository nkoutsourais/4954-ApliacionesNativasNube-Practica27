package es.urjc.code.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Response;

@FeignClient(name = "products", url = "http://monolito:8080/")
public interface ProductService {

    @RequestMapping(method = RequestMethod.POST, value = "api/products/{productId}/reserve", consumes = "application/json")
    Response reserve(@PathVariable("productId") Long productId, ReserveStock reserveStock);
}