package es.urjc.code.apigateway.products;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProductDestinations.class)
public class ProductConfiguration {

    @Bean
    public RouteLocator productProxyRouting(RouteLocatorBuilder builder, ProductDestinations productDestinations) {
        return builder.routes().route(p -> p.path("/api/products/**").uri(productDestinations.getProductServiceUrl()))
                .build();
    }
}