package es.urjc.code.apigateway.products;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "product.destinations")
public class ProductDestinations {

  @NotNull
  private String productServiceUrl;

  public String getProductServiceUrl() {
      return productServiceUrl;
  }

  public void setProductServiceUrl(String productServiceUrl) {
      this.productServiceUrl = productServiceUrl;
  }
}