# 4954-ApliacionesNativasNube-Practica27
## Practica 27
* Fecha de entrega: 30/04/2020
* Integrantes:
  * Neo Koutsourais
  
### Fase 1: Implementar un monolito básico
En esta fase para ejecutar el proyecto basta con ejecutar MonolitoApplication.java.

La creación del pedido se gestiona exclusivamente en OrderService.java, el pedido si no cumple que el cliente tenga credito y el producto tenga stock no se crea.
La tabla Orders tiene foreing keys a las tablas customers y products, por lo que al guardar el pedido se ven actualizados tanto el cliente como el producto en base de datos.

``` java
public Order createOrder(Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
    Product product = productService.get(customerId);
    product.reserveStock(quanty);
    Customer customer = customerService.get(productId);
    customer.reserveCredit(new Money(orderTotal));
    //Ha ido todo bien
    Order order = new Order(customer, product, quanty, new Money(orderTotal));
    this.orderRepository.save(order);
    return order;
}
```
Teniendo en el controller las siguientes salidas para las excepciones provocadas:

``` java
@PostMapping("/")
public ResponseEntity<OrderDto> newOrder(@RequestBody OrderDto orderDto) {
    try {
        Order order = this.orderService.createOrder(orderDto.getCustomerId(), orderDto.getProductId(), orderDto.getQuanty(), orderDto.getOrderTotal());
        orderDto.setOrderId(order.getId());
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    } catch (CustomerNotFoundException | ProductNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (CustomerCreditLimitExceededException | ProductStockLimitExceededException ex) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
```

Existe un servicio de notificaciones que es usado en el CustomerService.java

``` java
public void increaseCredit(Long customerId, Money extra) {
    Customer customer = get(customerId);
    customer.increaseCredit(extra);
    this.customerRepository.save(customer);
    Message message = new Message("El cliente " + customer.getName() + " ha recibido un ingreso de " + extra.toString());
    this.customerNotificationService.send(message);
}
```

El servicio simplemente escribe por consola el mensaje
``` java
public void send(Message message) {
    System.out.println(message.getMessage());
}
```
