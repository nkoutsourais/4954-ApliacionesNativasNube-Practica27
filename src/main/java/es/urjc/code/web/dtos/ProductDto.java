package es.urjc.code.web.dtos;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String name;
    private int stock;
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, int stock, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}