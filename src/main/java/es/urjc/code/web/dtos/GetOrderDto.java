package es.urjc.code.web.dtos;

import java.math.BigDecimal;

public class GetOrderDto {

    private Long orderId;
    private Long customerId;
    private Long productId;
    private int quanty;
    private BigDecimal orderTotal;

    public GetOrderDto() {
    }

    public GetOrderDto(Long orderId, Long customerId, Long productId, int quanty, BigDecimal orderTotal) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.quanty = quanty;
        this.orderTotal = orderTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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