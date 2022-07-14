package com.microservice.hibernate.controller;

import com.microservice.hibernate.dto.ProductData;
import com.microservice.hibernate.services.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Resource(name = "productService")
    private ProductService productService;

    /**
     * <p>Get all product data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<ProductData>
     */
    @GetMapping
    public List <ProductData> getProducts() {
        return productService.getAllProducts();
    }



    /**
     * Method to get the product data based on the ID.
     * @param id
     * @return ProductData
     */
    @GetMapping("/product/{id:\\d+}")
    public ProductData getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Post request to create customer information int the system.
     * @param productData
     * @return
     */
    @PostMapping("/create")
    public ProductData saveProduct(final @RequestBody ProductData productData) {
        return productService.saveProduct(productData);
    }


    /**
     * <p>Delete product from the system based on the ID. The method mapping is like the getProduct with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/product/{id:\\d+}")
    public Boolean deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


}