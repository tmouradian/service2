package com.microservice.hibernate.services;

import com.microservice.entity.Product;
import com.microservice.hibernate.dto.ProductData;
import com.microservice.hibernate.repository.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductService {

    @Autowired
    private ProductRepository productRepository;




    /**
     * Create a product based on the data sent to the service class.
     * @param product
     * @return DTO representation of the product
     */
    public ProductData saveProduct(ProductData product) {
        Product productModel = populateProductEntity(product);
        return populateProductData(productRepository.save(productModel));
    }


    /**
     * Delete product based on the customer ID.We can also use other option to delete product
     * based on the entity (passing JPA entity class as method parameter)
     * @param productId
     * @return boolean flag showing the request status
     */
    public boolean deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return true;
    }


    /**
     * Method to return the list of all the product in the system.This is a simple
     * implementation but use pagination in the real world example.
     * @return list of product
     */
    public List <ProductData> getAllProducts() {
        List <ProductData> products = new ArrayList < > ();
        List <Product> productList = productRepository.findAll();
        productList.forEach(product -> {
            products.add(populateProductData(product));
        });
        return products;
    }

    /**
     * Get product by ID. The service will send the product data else will throw the exception.
     * @param productId
     * @return ProductData.java
     */
    public ProductData getProductById(Long productId) {
        return populateProductData(productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found")));
    }



    /**
     * Internal method to convert Product JPA entity to the DTO object
     * for frontend data
     * @param product
     * @return ProductData.java
     */
    private ProductData populateProductData(final Product product) {
        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setProductName(product.getProductName());
        productData.setProductDescription(product.getProductDescription());
        productData.setPrice(product.getPrice());
        return productData;
    }




    /**
     * Method to map the front end product object to the JPA product entity.
     * @param productData
     * @return Product
     */
    private Product populateProductEntity(ProductData productData) {
        Product product = new Product();
        product.setProductName(productData.getProductName());
        product.setProductDescription(productData.getProductDescription());
        product.setPrice(productData.getPrice());

        return product;
    }


}