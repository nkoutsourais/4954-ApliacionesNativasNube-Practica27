package es.urjc.code.web.dtos;

public class CreateOrderRequestDto {

  private Long customerId;
  private Long productId;
  private int quanty;

  public CreateOrderRequestDto() {
  }

  public CreateOrderRequestDto(Long customerId, Long productId, int quanty) {
    this.customerId = customerId;
    this.productId = productId;
    this.quanty = quanty;
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
}