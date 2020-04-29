package es.urjc.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MonolitoApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MonolitoApplication.class, args);
    }
}