package es.urjc.code.domain.products;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stock;

    public Product() {
    }

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public void reserveStock(int orderQuantity) {
        if (stock >= orderQuantity)
            stock -= orderQuantity;
        else
            throw new ProductStockLimitExceededException();
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
}