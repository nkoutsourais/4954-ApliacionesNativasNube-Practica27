package es.urjc.code.apigateway.webhook;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WebhookDestinations.class)
public class WebhookConfiguration {

    @Bean
    public RouteLocator webhookProxyRouting(RouteLocatorBuilder builder, WebhookDestinations webhookDestinations) {
        return builder.routes().route(p -> p.path("/api/webh/**").uri(webhookDestinations.getWebhookServiceUrl()))
                .build();
    }
}