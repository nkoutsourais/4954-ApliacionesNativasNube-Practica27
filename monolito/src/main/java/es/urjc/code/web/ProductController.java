package es.urjc.code.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.domain.Money;
import es.urjc.code.domain.customers.CustomerNotFoundException;
import es.urjc.code.domain.products.Product;
import es.urjc.code.services.ProductService;
import es.urjc.code.web.dtos.ProductDto;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
	public List<ProductDto> getAll() {
		return productService.getAll().stream()
                .map(product -> mapper(product))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getPost(@PathVariable Long productId) {
        try
        {
            Product product = this.productService.get(productId);
            return new ResponseEntity<>(mapper(product), HttpStatus.OK);
        } catch(CustomerNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
	public ResponseEntity<ProductDto> newProduct(@RequestBody ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getStock(), new Money(productDto.getPrice()));
		this.productService.add(product);
		return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }
    
    private ProductDto mapper(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getStock(), product.getPrice().getAmount());
    }
}