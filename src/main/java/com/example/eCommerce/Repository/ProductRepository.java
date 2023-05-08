package com.example.eCommerce.Repository;

import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByProductCategory(ProductCategory productCategory);

    @Query(value = "select * from product  p where p.price > :price and p.product_category=:productCategory",nativeQuery = true)
    List<Product> getAllProductByPriceAndCategory(int price,String productCategory);

    @Query(value = "select * from product ORDER BY Price DESC, Name ASC LIMIT 0,5",nativeQuery = true)
    List<Product> top5CostliestProduct();

    @Query(value = "select * from product ORDER BY Price ASC, Name ASC LIMIT 0,5",nativeQuery = true)
    List<Product> top5cheapestProduct();

    @Query(value = "select * from  product p where p.product_category=:category ORDER BY Price DESC, Name ASC LIMIT 0,5",nativeQuery = true)
    List<Product>top5CostliestProductOfParticularCategory(String category);
    @Query(value = "select * from  product p where p.product_category=:category ORDER BY Price ASC, Name ASC LIMIT 0,5",nativeQuery = true)
    List<Product> top5CheapestProductOfParticularCategory(String category);


}
