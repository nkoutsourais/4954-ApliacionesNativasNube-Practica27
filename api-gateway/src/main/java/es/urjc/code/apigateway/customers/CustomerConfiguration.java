package es.urjc.code.apigateway.customers;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CustomerDestinations.class)
public class CustomerConfiguration {

    @Bean
    public RouteLocator customerProxyRouting(RouteLocatorBuilder builder, CustomerDestinations customerDestinations) {
        return builder.routes().route(p -> p.path("/api/customers/**").uri(customerDestinations.getCustomerServiceUrl()))
                .build();
    }
}