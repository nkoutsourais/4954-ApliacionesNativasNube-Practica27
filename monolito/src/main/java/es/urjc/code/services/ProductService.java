package es.urjc.code.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.code.domain.products.Product;
import es.urjc.code.domain.products.ProductNotFoundException;
import es.urjc.code.domain.products.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product get(Long productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    public void add(Product product) {
        this.productRepository.save(product);
    }

    public BigDecimal reserveStock(Long productId, int quanty) {
        Product product = get(productId);
        product.reserveStock(quanty);
        this.productRepository.save(product);
        return product.calculatePrice(quanty).getAmount();
	}
}