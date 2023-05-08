package com.example.eCommerce.Repository;

import com.example.eCommerce.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller findByEmailId(String emailId);

    String deleteByEmailId(String email);
}
