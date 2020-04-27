package es.urjc.code.web.dtos;

import java.math.BigDecimal;

public class CreateOrderRequestDto {

  private Long customerId;
  private Long productId;
  private int quanty;
  private BigDecimal orderTotal;

  public CreateOrderRequestDto() {
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuanty() {
    return quanty;
  }

  public void setQuanty(int quanty) {
    this.quanty = quanty;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
  }
}