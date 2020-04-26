package es.urjc.code.domain.customers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.orders.Order;

@Entity
@Table(name="Customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Embedded
  private Money creditLimit;

  @OneToMany(mappedBy = "customer")
  private List<Order> orders = new ArrayList<>();

  public Customer() {
  }

  public Customer(String name, Money creditLimit) {
    this.name = name;
    this.creditLimit = creditLimit;
  }

  public void reserveCredit(Money orderTotal) {
    if (creditLimit.isGreaterThanOrEqual(orderTotal)) {
      creditLimit = creditLimit.subtract(orderTotal);
    } else
      throw new CustomerCreditLimitExceededException();
  }

  public void increaseCredit(Money extra) {
    creditLimit = creditLimit.add(extra);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public Money getCreditLimit() {
    return creditLimit;
  }
}
