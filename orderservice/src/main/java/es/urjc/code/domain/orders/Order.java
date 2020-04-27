package es.urjc.code.domain.orders;

import javax.persistence.*;

import es.urjc.code.domain.Money;

@Entity
@Table(name="Orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long customerId;
  private Long productId;
  private int quanty;
  @Embedded
  private Money orderTotal;

  public Order() {
  }

  public Order(Long customerId, Long productId, int quanty, Money orderTotal) {
    this.quanty = quanty;
    this.productId = productId;
    this.customerId = customerId;
    this.orderTotal = orderTotal;
  }

  public Long getId() {
    return id;
  }

  public Long getProductId() {
    return this.productId;
  }

  public Long getCustomerId() {
    return this.customerId;
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