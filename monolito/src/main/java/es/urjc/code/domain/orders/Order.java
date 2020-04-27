package es.urjc.code.domain.orders;

import javax.persistence.*;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.Customer;
import es.urjc.code.domain.products.Product;

@Entity
@Table(name="Orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Customer customer;
  @ManyToOne
  private Product product;
  private int quanty;
  @Embedded
  private Money orderTotal;

  public Order() {
  }

  public Order(Customer customer, Product product, int quanty, Money orderTotal) {
    this.quanty = quanty;
    this.product = product;
    this.customer = customer;
    this.orderTotal = orderTotal;
  }

  public Long getId() {
    return id;
  }

  public Long getProductId() {
    return this.product.getId();
  }

  public Long getCustomerId() {
    return this.customer.getId();
  }

  public int getQuanty() {
    return this.quanty;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Money getOrderTotal() {
    return this.orderTotal;
  }
}