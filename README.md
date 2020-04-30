# 4954-ApliacionesNativasNube-Practica27
## Practica 27
* Fecha de entrega: 30/04/2020
* Integrantes:
  * Neo Koutsourais
  
### Fase 3: Aplicar el patrón branch by abstraction para las notificaciones
En esta fase para ejecutar el proyecto se deben seguir estos dos pasos:

``` sh
node install.js
docker-compose -f "docker-compose.yml" up -d --build
```

En el primer paso se llevaran acabo las instalaciones de los proyectos Java y en el segundo el despliegue en Docker.

Se ha creado un proyecto nuevo:

``` yml
version: '3'
services:
  notificationservice:
    build: ./notificationservice

  monolith:
    build: ./monolito
    depends_on: 
      - notificationservice
    environment:
      - feature.toggle.notification.proxy=true
      - notification.server=http://notificationservice:8080/

  orderservice:
    build: ./orderservice
    environment:
      - monolith.server=http://monolith:8080/

  apigateway:
    build: ./api-gateway
    depends_on: 
      - monolith
      - orderservice
    ports:
      - "8080:8080"
    environment:
      - order.destinations.orderServiceUrl=http://orderservice:8080/
      - webhook.destinations.webhookServiceUrl=http://monolith:8080/
      - customer.destinations.customerServiceUrl=http://monolith:8080/
      - product.destinations.productServiceUrl=http://monolith:8080/
```

  - NotificationService:
    Nuevo microserEncargado de recibir mensajes y notificarlos.
  
  - Monolito:
    Se modifica para que que mediante una variable en el despliegue podamos definir que servicio queremos que notifique, si el incluido en el monolito o una llamada al nuevo microservicio NotificationService.
    
    Para ello se ha cambiado el servicio que se inyectaba en el CustomerService por CustomerNotificationFeatureToggle, comprueba el valor de la variable e invoca el servicio adecuado. 

``` java
@Service
public class CustomerNotificationFeatureToggle implements CustomerNotification {

    @Value("${feature.toggle.notification.proxy}")
    private boolean proxy = false;

    @Autowired
    CustomerNotificationProxyService customerNotificationProxyService;
    @Autowired
    CustomerNotificationService customerNotificationService;

    @Override
    public void send(Message message) {
        if(proxy)
            customerNotificationProxyService.send(message);
        else
            customerNotificationService.send(message);
    }
}
```

  Para las llamadas al microservicio se ha creado un proxie con **Feign** 

``` java
@FeignClient(name = "notifications", url = "${notification.server}")
public interface CustomerNotificationProxy {
    
    @RequestMapping(method = RequestMethod.POST, value = "api/notifications", consumes = "application/json")
    Response send(Message message);
}
```   
