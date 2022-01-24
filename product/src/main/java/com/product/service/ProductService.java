package com.product.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.product.entity.ProductAvailabilityDTO;
import com.product.entity.ProductDTO;
import com.product.exception.ProductNotFoundException;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    Logger logger = (Logger) LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${catalog.url}")
    private String catalogUrl;

    @Value("${inventory.url}")
    private String inventoryUrl;

    @HystrixCommand(fallbackMethod = "getFallBackProduct",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "30000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
                            value = "2"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "500")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30")
            })
    public ProductDTO findProductByUniqId(String uniqId) {
        logger.info("Start execution findProductByUniqId service.");
        ResponseEntity<ProductDTO> product = restTemplate.exchange(catalogUrl + uniqId +"/id",
                HttpMethod.GET,
                null,
                ProductDTO.class);
        ResponseEntity<ProductAvailabilityDTO> availableProduct = restTemplate.exchange(inventoryUrl + uniqId,
                HttpMethod.GET,
                null,
                ProductAvailabilityDTO.class);
        if (!availableProduct.hasBody() || availableProduct.getBody().getNumberOfAvailableProduct() == 0){
            throw new ProductNotFoundException("Product with uniqId ["+uniqId+"] doesn't exist");
        }
        logger.info("Finish execution findProductByUniqId service.");
        return product.getBody();
    }

    public ProductDTO getFallBackProduct(String uniqId){
        throw new ProductNotFoundException("Product has not found");
    }
    public List<ProductDTO> getFallBackProducts(String sku){
        throw new ProductNotFoundException("Product has not found");
    }

    @HystrixCommand(fallbackMethod = "getFallBackProducts",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "30000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
                            value = "2"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "500")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30")
            })
    public List<ProductDTO> findProductBySku(String sku) {
        logger.info("Start execution findProductBySku service.");
        List<ProductDTO> availableProducts = new ArrayList<>();
        logger.info("Call url: %s", catalogUrl);
        ResponseEntity<List<ProductDTO>> products = restTemplate.exchange(catalogUrl + sku + "/sku",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {});

        for (ProductDTO productDTO: products.getBody()) {
            logger.info("Call url: %s", inventoryUrl);
            ResponseEntity<ProductAvailabilityDTO> product = restTemplate.exchange(inventoryUrl + productDTO.getUniqId(),
                    HttpMethod.GET,
                    null,
                    ProductAvailabilityDTO.class);
            if (product.hasBody() && product.getBody().getNumberOfAvailableProduct() > 0){
                availableProducts.add(productDTO);
            }
        }
        if (availableProducts.isEmpty()){
            throw new ProductNotFoundException("Product with sku ["+sku+"] doesn't exist");
        }
        logger.info("Finish execution findProductBySku service.");
        return availableProducts;
    }
}
