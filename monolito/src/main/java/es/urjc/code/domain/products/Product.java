package es.urjc.code.domain.products;

import java.math.BigDecimal;

import javax.persistence.*;

import es.urjc.code.domain.Money;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stock;
    @Embedded
    private Money price;

    public Product() {
    }

    public Product(String name, int stock, Money price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public void reserveStock(int orderQuantity) {
        if (stock >= orderQuantity)
            stock -= orderQuantity;
        else
            throw new ProductStockLimitExceededException();
    }

    public Money calculatePrice(int quanty) {
        BigDecimal price = this.price.getAmount();
        return new Money(price.multiply(new BigDecimal(quanty)));
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

    public int getStock() {
        return stock;
    }

    public Money getPrice() {
        return price;
    }
}