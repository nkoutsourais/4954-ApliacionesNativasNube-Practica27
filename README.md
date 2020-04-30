# 4954-ApliacionesNativasNube-Practica27
## Practica 27
* Fecha de entrega: 30/04/2020
* Integrantes:
  * Neo Koutsourais
  
### Fase 2: Aplicar el patrón strangler fig para Pedidos 
En esta fase para ejecutar el proyecto se deben seguir estos dos pasos:

``` sh
node install.js
docker-compose -f "docker-compose.yml" up -d --build
```

En el primer paso se llevaran acabo las instalaciones de los proyectos Java y en el segundo el despliegue en Docker.

Se han creado dos proyectos nuevos:

``` yml
version: '3'
services:
  monolith:
      build: ./monolito

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
 - Apigateway:
    Encargado de distribuir las llamadas que se hacen al puerto 8080, será el unico proyecto expuesto al exterior de Docker.
 
 - OrderService:
    Nuevo microservicio encargado de la gestión de pedidos
    
 - Monolito:
    Desaparece todo lo relacionado con pedidos.
    
La creación del pedido que ahora se gestiona en el microservicio OrderService se realiza mediante una saga para crear la compensación necesaría en el cliente si despues de reservar credito resulta no haber stock del Producto, se mantienen el mismo tipo de excepciones por lo que OrderController.java no es necesario tocarlo.

El modelo de datos de Orders también cambia pues ahora en la tabla no existen relaciones con Customer y Product como en el monolito.

CreateOrderSaga.java

``` java
public Order execute(Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
    Response response = webhookService.reserve(customerId, new CreditDto(orderTotal));
    if(response.status() == HttpStatus.OK.value()) {
        response = webhookService.reserve(productId, new ReserveStock(quanty));
        if(response.status() == HttpStatus.OK.value()) {
            //Ha ido todo bien
            Order order = new Order(customerId, productId, quanty, new Money(orderTotal));
            this.orderRepository.save(order);
            return order;
        } else if(response.status() == HttpStatus.NOT_FOUND.value()) {
            throw new ProductNotFoundException();
        } else if(response.status() == HttpStatus.FORBIDDEN.value()) {
            response = webhookService.addcredit(customerId, new CreditDto(orderTotal));
            throw new ProductStockLimitExceededException();
        }
    } else if(response.status() == HttpStatus.NOT_FOUND.value()) {
        throw new CustomerNotFoundException();
    } else if(response.status() == HttpStatus.FORBIDDEN.value()) {
        throw new CustomerCreditLimitExceededException();
    }
    throw new RuntimeException("Error no controlado");
}
```
Para las llamadas de reserva de credito y stock como la compensación de credito se ha creado unos proxie con **Feign** encargado de hacer las llamadas al WebhookController del monolito.

WebhookService.java

``` java
@FeignClient(name = "webhooks", url = "${monolith.server}")
public interface WebhookService {

    @RequestMapping(method = RequestMethod.POST, value = "api/webh/products/{productId}/reserve", consumes = "application/json")
    Response reserve(@PathVariable("productId") Long productId, ReserveStock reserveStock);

    @RequestMapping(method = RequestMethod.POST, value = "api/webh/customers/{customerId}/credit", consumes = "application/json")
    Response addcredit(@PathVariable("customerId") Long customerId, CreditDto creditExtra);

    @RequestMapping(method = RequestMethod.POST, value = "api/webh/customers/{customerId}/reserve", consumes = "application/json")
    Response reserve(@PathVariable("customerId") Long customerId, CreditDto reserveCredit);
}
```
