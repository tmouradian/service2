package com.microservice.hibernate.repository.Product;

import com.microservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}

