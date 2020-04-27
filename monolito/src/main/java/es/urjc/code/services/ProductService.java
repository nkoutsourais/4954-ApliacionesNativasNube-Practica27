package es.urjc.code.services;

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

    public Product add(String name, int stock) {
        Product product = new Product(name, stock);
        this.productRepository.save(product);
        return product;
    }
}